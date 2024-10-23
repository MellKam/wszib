import * as THREE from "three";
import { OrbitControls } from "three/addons/controls/OrbitControls.js";

var scene = new THREE.Scene();
var camera = new THREE.PerspectiveCamera(
	75,
	window.innerWidth / window.innerHeight,
	0.1,
	1000
);

var renderer = new THREE.WebGLRenderer();
renderer.shadowMap.enabled = true;
renderer.setSize(window.innerWidth, window.innerHeight);
document.body.appendChild(renderer.domElement);

const controls = new OrbitControls(camera, renderer.domElement);

// const gridHelper = new THREE.GridHelper(24, 24);
// scene.add(gridHelper);

camera.position.z = 5;
camera.position.y = 6;
camera.lookAt(0, 2, 0);
controls.update();

camera.position.z = 5;

let ballsCount = 10;
let step = (2 * Math.PI) / ballsCount;

function createBallsRing({
	ballsCount,
	radius,
}: {
	radius: number;
	ballsCount: number;
}) {
	const group = new THREE.Group();
	const step = (2 * Math.PI) / ballsCount;

	for (let i = 0; i < ballsCount; i++) {
		const ball = new THREE.Mesh(
			new THREE.SphereGeometry(0.5, 32, 32),
			new THREE.MeshStandardMaterial({ color: 0xff0000 })
		);

		ball.castShadow = true;

		ball.position.x = radius * Math.cos(i * step);
		ball.position.z = radius * Math.sin(i * step);

		group.add(ball);
	}

	return group;
}

const ring = createBallsRing({
	ballsCount,
	radius: 3,
});
scene.add(ring);

ring.position.y = 2;

function createSquareGridTexture(
	width: number,
	height: number,
	rows: number,
	columns: number
): THREE.CanvasTexture {
	const canvas = document.createElement("canvas");
	canvas.width = width;
	canvas.height = height;

	const ctx = canvas.getContext("2d");

	if (!ctx) {
		throw new Error("Could not get canvas context");
	}

	const cellWidth = 20;
	const cellHeight = 20;
	const gap = 10;

	ctx.fillStyle = "yellow";
	ctx.fillRect(0, 0, width, height);

	ctx.fillStyle = "black";
	for (let i = 0; i < rows; i++) {
		for (let j = 0; j < columns; j++) {
			ctx.fillRect(
				j * (cellWidth + gap),
				i * (cellHeight + gap),
				cellWidth,
				cellHeight
			);
		}
	}

	return new THREE.CanvasTexture(canvas);
}

const planeGeometry = new THREE.PlaneGeometry(20, 20, 10, 10);
const plane = new THREE.Mesh(
	planeGeometry,
	new THREE.MeshStandardMaterial({
		map: createSquareGridTexture(256, 256, 9, 9),
	})
);

plane.receiveShadow = true;
plane.rotation.x = -Math.PI / 2;

scene.add(plane);

const ambientLight = new THREE.AmbientLight(0xffffff, 0.7);
scene.add(ambientLight);
const light = new THREE.DirectionalLight(0xffffff, 2);
light.castShadow = true;
light.position.set(0, 5, 0);
light.scale.set(20, 20, 20);
light.shadow.mapSize.width = 1024;
light.shadow.mapSize.height = 1024;
light.shadow.camera.far = 1000;
light.shadow.camera.left = -10;
light.shadow.camera.right = 10;
light.shadow.camera.top = 10;
light.shadow.camera.bottom = -10;
scene.add(light);

const lightHelper = new THREE.DirectionalLightHelper(light, 5);
scene.add(lightHelper);

let isExploding = false;
let explosionSpeed = 0.1;

window.addEventListener("click", () => {
	isExploding = !isExploding;
});

var animate = function () {
	requestAnimationFrame(animate);

	if (isExploding) {
		ring.children.forEach((ball, index) => {
			ball.position.x += explosionSpeed * Math.cos(index * step);
			ball.position.z += explosionSpeed * Math.sin(index * step);
		});
	}

	renderer.render(scene, camera);
};

animate();

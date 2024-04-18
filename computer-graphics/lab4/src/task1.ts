import * as THREE from "three";

export function task1() {
	const scene = new THREE.Scene();
	const camera = new THREE.PerspectiveCamera(
		75,
		window.innerWidth / window.innerHeight,
		0.1,
		1000
	);

	const renderer = new THREE.WebGLRenderer();
	renderer.setSize(window.innerWidth, window.innerHeight);
	renderer.shadowMap.enabled = true;
	document.body.appendChild(renderer.domElement);

	const canvas = document.createElement("canvas");
	canvas.width = 128;
	canvas.height = 128;
	const context = canvas.getContext("2d");
	if (!context) {
		throw new Error("Canvas 2D context is not supported");
	}

	for (let x = 0; x < 128; x += 64) {
		for (let y = 0; y < 128; y += 64) {
			context.fillStyle = (x + y) % 128 === 0 ? "#ffffff" : "#000000";
			context.fillRect(x, y, 64, 64);
		}
	}

	const texture = new THREE.CanvasTexture(canvas);
	texture.wrapS = THREE.RepeatWrapping;
	texture.wrapT = THREE.RepeatWrapping;
	texture.repeat.set(4, 4);

	const geometry = new THREE.BoxGeometry();
	const material = new THREE.MeshLambertMaterial({ color: 0x00ff00 });
	const cube = new THREE.Mesh(geometry, material);
	scene.add(cube);
	cube.castShadow = true;

	const directionalLight = new THREE.DirectionalLight(0xffffff, 1.0);
	scene.add(directionalLight);
	directionalLight.castShadow = true;
	directionalLight.position.x = 1;
	directionalLight.position.z = 1;

	camera.position.z = 5;

	const floorGeometry = new THREE.PlaneGeometry(5, 5);
	const floorMaterial = new THREE.MeshLambertMaterial({ map: texture });
	const floor = new THREE.Mesh(floorGeometry, floorMaterial);
	scene.add(floor);
	floor.rotation.x = -Math.PI / 2.0;
	floor.position.y = -1.0;
	floor.receiveShadow = true;

	function animate() {
		requestAnimationFrame(animate);
		cube.rotation.x += 0.01;
		cube.rotation.y += 0.01;
		renderer.render(scene, camera);
	}

	animate();
}

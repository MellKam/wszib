import * as THREE from "three";

export function task3() {
	const scene = new THREE.Scene();
	const camera = new THREE.PerspectiveCamera(
		75,
		window.innerWidth / window.innerHeight,
		0.1,
		1000
	);

	const renderer = new THREE.WebGLRenderer();
	renderer.setSize(window.innerWidth, window.innerHeight);
	document.body.appendChild(renderer.domElement);

	var mouseDown = false;
	var lastMouseX: number | null = null;
	var lastMouseY: number | null = null;

	document.addEventListener("mousedown", function (event) {
		mouseDown = true;
		lastMouseX = event.clientX;
		lastMouseY = event.clientY;
	});

	document.addEventListener("mouseup", function () {
		mouseDown = false;
	});

	document.addEventListener("mousemove", function (event) {
		if (!mouseDown) return;

		var deltaX = event.clientX - lastMouseX!;
		var deltaY = event.clientY - lastMouseY!;

		lastMouseX = event.clientX;
		lastMouseY = event.clientY;

		rotateCube(deltaX, deltaY);
	});

	const directionalLight = new THREE.AmbientLight(0xffffff, 2.0);
	scene.add(directionalLight);
	directionalLight.position.x = 1;
	directionalLight.position.z = 1;

	function rotateCube(deltaX: number, deltaY: number) {
		scene.rotation.y += deltaX * 0.01;
		scene.rotation.x += deltaY * 0.01;
	}

	function generateDiceTexture(dotCount: number) {
		var canvas = document.createElement("canvas");
		canvas.width = 256;
		canvas.height = 256;
		var context = canvas.getContext("2d");
		if (!context) {
			throw new Error("Canvas 2D context is not supported");
		}

		context.fillStyle = "#ffffff";
		context.fillRect(0, 0, 256, 256);

		context.fillStyle = "#000000";
		switch (dotCount) {
			case 1:
				context.beginPath();
				context.arc(128, 128, 20, 0, Math.PI * 2);
				context.fill();
				context.closePath();
				break;
			case 2:
				context.beginPath();
				context.arc(64, 64, 20, 0, Math.PI * 2);
				context.fill();
				context.closePath();
				context.arc(192, 192, 20, 0, Math.PI * 2);
				context.fill();
				context.closePath();
				break;
			case 3:
				context.beginPath();
				context.arc(64, 64, 20, 0, Math.PI * 2);
				context.fill();
				context.closePath();
				context.arc(128, 128, 20, 0, Math.PI * 2);
				context.fill();
				context.closePath();
				context.arc(192, 192, 20, 0, Math.PI * 2);
				context.fill();
				context.closePath();
				break;
			case 4:
				context.beginPath();
				context.arc(64, 64, 20, 0, Math.PI * 2);
				context.fill();
				context.closePath();
				context.arc(64, 192, 20, 0, Math.PI * 2);
				context.fill();
				context.closePath();
				context.arc(192, 64, 20, 0, Math.PI * 2);
				context.fill();
				context.closePath();
				context.arc(192, 192, 20, 0, Math.PI * 2);
				context.fill();
				context.closePath();
				break;
			case 5:
				context.beginPath();
				context.arc(64, 64, 20, 0, Math.PI * 2);
				context.fill();
				context.closePath();
				context.arc(64, 192, 20, 0, Math.PI * 2);
				context.fill();
				context.closePath();
				context.arc(128, 128, 20, 0, Math.PI * 2);
				context.fill();
				context.closePath();
				context.arc(192, 64, 20, 0, Math.PI * 2);
				context.fill();
				context.closePath();
				context.arc(192, 192, 20, 0, Math.PI * 2);
				context.fill();
				context.closePath();
				break;
			case 6:
				context.beginPath();
				context.arc(64, 48, 20, 0, Math.PI * 2);
				context.fill();
				context.closePath();
				context.arc(64, 128, 20, 0, Math.PI * 2);
				context.fill();
				context.closePath();
				context.arc(64, 208, 20, 0, Math.PI * 2);
				context.fill();
				context.closePath();
				context.arc(192, 48, 20, 0, Math.PI * 2);
				context.fill();
				context.closePath();
				context.arc(192, 128, 20, 0, Math.PI * 2);
				context.fill();
				context.closePath();
				context.arc(192, 208, 20, 0, Math.PI * 2);
				context.fill();
				context.closePath();
				break;
		}

		var texture = new THREE.CanvasTexture(canvas);
		return texture;
	}

	const geometry = new THREE.BoxGeometry();
	const materials = [
		new THREE.MeshLambertMaterial({ map: generateDiceTexture(1) }),
		new THREE.MeshLambertMaterial({ map: generateDiceTexture(6) }),
		new THREE.MeshLambertMaterial({ map: generateDiceTexture(3) }),
		new THREE.MeshLambertMaterial({ map: generateDiceTexture(4) }),
		new THREE.MeshLambertMaterial({ map: generateDiceTexture(5) }),
		new THREE.MeshLambertMaterial({ map: generateDiceTexture(2) }),
	];
	const cube = new THREE.Mesh(geometry, materials);
	scene.add(cube);

	camera.position.z = 5;

	const animate = function () {
		requestAnimationFrame(animate);
		cube.rotation.x += 0.01;
		cube.rotation.y += 0.01;
		renderer.render(scene, camera);
	};

	animate();
}

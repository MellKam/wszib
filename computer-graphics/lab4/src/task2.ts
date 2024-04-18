import * as THREE from "three";

export function task2() {
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

	const textureLoader = new THREE.TextureLoader();

	const floorTexture = textureLoader.load("/tame_impala.jpeg");
	const cubeTexture = textureLoader.load("/sho.webp");
	const sphereTexture = textureLoader.load("/bojack.webp");
	const torusTexture = textureLoader.load("/trench.jpeg");

	const geometry = new THREE.BoxGeometry();
	const cubeMaterial = new THREE.MeshLambertMaterial({ map: cubeTexture });
	const cube = new THREE.Mesh(geometry, cubeMaterial);
	scene.add(cube);
	cube.castShadow = true;

	const sphereGeometry = new THREE.SphereGeometry(1, 32, 32);
	const sphereMaterial = new THREE.MeshLambertMaterial({ map: sphereTexture });
	const sphere = new THREE.Mesh(sphereGeometry, sphereMaterial);
	sphere.position.set(-2, 0, 0);
	scene.add(sphere);
	sphere.castShadow = true;

	const torusGeometry = new THREE.TorusGeometry(1, 0.4, 32, 32);
	const torusMaterial = new THREE.MeshLambertMaterial({ map: torusTexture });
	const torus = new THREE.Mesh(torusGeometry, torusMaterial);
	torus.position.set(2, 0, 0);
	scene.add(torus);
	torus.castShadow = true;

	const directionalLight = new THREE.DirectionalLight(0xffffff, 1.0);
	scene.add(directionalLight);
	directionalLight.castShadow = true;
	directionalLight.position.x = 1;
	directionalLight.position.z = 1;

	camera.position.z = 5;

	const floorGeometry = new THREE.PlaneGeometry(5, 5);
	const floorMaterial = new THREE.MeshLambertMaterial({ map: floorTexture });
	const floor = new THREE.Mesh(floorGeometry, floorMaterial);
	scene.add(floor);
	floor.rotation.x = -Math.PI / 2.0;
	floor.position.y = -1.0;
	floor.receiveShadow = true;

	const animate = function () {
		requestAnimationFrame(animate);
		cube.rotation.x += 0.01;
		cube.rotation.y += 0.01;
		sphere.rotation.x += 0.01;
		torus.rotation.x += 0.01;
		renderer.render(scene, camera);
	};

	animate();
}

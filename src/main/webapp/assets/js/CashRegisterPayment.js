let totalPrice = 0;

document.addEventListener("DOMContentLoaded", function() {
	const hiddenInput = document.getElementById('js-total-price');
	if (hiddenInput) {
		totalPrice = parseInt(hiddenInput.value);
	}
});

function addNumber(num) {
	const input = document.getElementById('depositInput');
	if (input.value === '0' && num === '0') return;
	input.value += num;
	calcChange();
}
function clearInput() {
	document.getElementById('depositInput').value = '';
	calcChange();
}
function calcChange() {
	const input = document.getElementById('depositInput');
	const changeDisplay = document.getElementById('changeDisplay');
	const deposit = parseInt(input.value) || 0;
	const change = deposit - totalPrice;

	if (deposit > 0) {
		if (change >= 0) {
			changeDisplay.innerText = "お釣り: ¥ " + change.toLocaleString();
			changeDisplay.style.color = "blue";
		} else {
			changeDisplay.innerText = "不足: ¥ " + Math.abs(change).toLocaleString();
			changeDisplay.style.color = "red";
		}
	} else {
		changeDisplay.innerText = "";
	}
}
// 人数を増減させるJavaScript
function updateCount(change) {
	const input = document.getElementById('countInput');
	let currentVal = parseInt(input.value);

	// 計算
	let newVal = currentVal + change;

	// 最小値は1人、最大値は99人（必要に応じて変更可）
	if (newVal < 1) {
		newVal = 1;
	} else if (newVal > 16) {
		newVal = 16;
	}

	input.value = newVal;
}

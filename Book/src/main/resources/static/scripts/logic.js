var d = document;

// Функция кроссбраузерная установка обработчика событий
function addEvent(elem, type, handler){
    if(elem.addEventListener){
        elem.addEventListener(type, handler, false);
    } else {
        elem.attachEvent('on'+type, function(){ handler.call( elem ); });
    }
    return false;
}

// Открываем корзину со списком добавленных товаров
function openCart(){
	$.get('/', function(data) {
		let answer = "";

		if (data != null) {
			answer += '<strong>ANSWER</strong>' +
				'<span id="total_count" style="margin-left: 10px">'+ data +'</span>';
		} else {
			answer += '<span>Error!</span>';
		}

		$("#cart_content").html(answer);
	});
}

function del_book() {
	$.ajax({
		url: '/',
		dataType: 'json',
		type: 'POST',
		cache: 'false',
		contentType: 'application/json',
		data: JSON.stringify({
			numberF: $("#first_number").val(),
			numberS: $("#second_number").val()
		}),
		success: function(data) {
			console.log(data);
		},
		error: function (data) {
			console.log(data);
		}
	});

	openCart()
}

/* Очистить корзину */
addEvent(d.getElementById('del_book'), 'click', del_book);
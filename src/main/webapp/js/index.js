$(document).ready(function () {
    loadAllItems();
    setInterval(loadAllItems, 180000);
});

function loadAllItems() {
    $.ajax({
        url: '/all-advertisements',
        type: 'POST',
        data: false,
        dataType: "text",
    }).done(function (data) {
        data = JSON.parse(data);
        drawAdvertisements(data)
    }).fail(function (error) {
        console.log(error)
    });
}

function drawAdvertisements(listItems) {
    $('.all-items .block-auto').remove();
    let items = listItems.advertisements;
    $(items).each(function (index, item) {
        let state = item.car.kmAge;
        let status = item.saleStatus;
        if (status) {
            status = ">Продан</p>";
        } else {
            status = "id='status-color'>В продаже</p>";
        }
        if (state === "") {
            state = "Новый";
        } else {
            state += " km";
        }
        $('.all-items').append("<div class='block-auto'>"
            + "<div class='containers'>"
            + "<img src='/download?id=" + item.id + "' onerror=\"this.src='css/empty.jpg'\" class='block-img'>"
            + "<input hidden id='car-index' value='" + index + "'>"
            + "<div class='block-description'>"
            + "<h1 class='section-title'>" + item.car.name + "</h1>"
            + "<div class='box-item'>"
            + "<div class='common_content-block'>"
            + "<div class='block-text'>"
            + "<div class='box-text_1'>"
            + "<p class='cell-item'>" + item.car.engine.horsePower + "л.с.</p>"
            + "<p class='cell-item'>" + item.car.engine.fuelType + "</p>"
            + "<p class='cell-item'>" + item.car.carBody + "</p>"
            + "</div>"
            + "<div class='box-text_2'>"
            + "<p class='cell-item'>" + item.car.wheelDrive + "</p>"
            + "<p class='cell-item'>" + item.car.transmission + "</p>"
            + "<p class='cell-item' " + status
            + "</div>"
            + "</div>"
            + "<div class='box-listItem'>"
            + "<div class='listItem'>"
            + "<label class='cell-text'>Цена:</label>"
            + "<p class='cell-listItem'>" + item.price + " ₽</p>"
            + "</div>"
            + "<div class='listItem'>"
            + "<label class='cell-text'>Год выпуска:</label>"
            + "<p class='cell-listItem'>" + item.car.yearOfIssue + "</p>"
            + "</div>"
            + "<div class='listItem'>"
            + "<label class='cell-text'>Состояние:</label>"
            + "<p class='cell-listItem'>" + state + "</p>"
            + "</div>"
            + "</div>"
            + "</div>"
            + "</div>"
            + "</div>"
            + " </div>"
            + "</div>")
            .on('click', '.block-auto', function (event) {
                let indexCar = $(this).find('#car-index').val();
                localStorage.clear();
                localStorage.setItem("car", JSON.stringify(items[indexCar]));
                localStorage.setItem("statusId", JSON.stringify(listItems.statusId));
                window.location.href = "/single-advertisement";
                event.stopImmediatePropagation();
                return false;
            });
    })
}
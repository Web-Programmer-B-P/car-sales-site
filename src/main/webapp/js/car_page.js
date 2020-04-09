$(document).ready(function () {
    let advert = JSON.parse(localStorage.getItem("car"));
    let statusId = JSON.parse(localStorage.getItem("statusId"));
    $('#label-ph').on('click', function () {
        getPhone(advert.userId);
    });

    $('#back-home').on('click', function () {
        let status = $('#flag-sale').is(':checked');
        if (statusId === advert.userId) {
            markAsSold(status, advert.id, statusId);
        } else {
            window.location.href = '/all-advertisements';
        }
    });

    $('.name_car').text(advert.car.name);
    $('.car-price').text(advert.price + ' ₽');
    $('.year').text(advert.car.yearOfIssue);
    if (advert.car.kmAge === '') {
        $('.km-age').text('Новый');
    } else {
        $('.km-age').text(advert.car.kmAge + ' Км');
    }
    $('.car-body').text(advert.car.carBody);
    $('.car-color').text(advert.car.color);
    $('.fuel-type-horse-power').text(advert.car.engine.horsePower + " л.с / " + advert.car.engine.fuelType);
    $('.transmiss').text(advert.car.transmission);
    $('.wheel-drive').text(advert.car.wheelDrive);
    $('.handlebar').text(advert.car.handlebar);
    $('.car-description').text(advert.description);
    $('.box_info').append("<img src='/download?id=" + advert.id + "' onerror=\"this.src='css/empty.jpg'\" class='car_image'>");

    if (statusId === advert.userId) {
        $('.box_checkbox').show();
    }
    if (advert.saleStatus) {
        $('#flag-sale').attr('checked', true);
        $('.sale-status').text("Продан");
    } else {
        $('#flag-sale').attr('checked', false);
        $('.sale-status').text("В продаже");
    }
});

function getPhone(userId) {
    $.ajax({
        url: '/get-phone',
        type: 'POST',
        data: {userId: userId},
        dataType: "text",
    }).done(function (phone) {
        phone = JSON.parse(phone);
        $('.show_phone').val(phone);
        console.log(phone)
    }).fail(function (error) {
        console.log(error)
    });
}

function markAsSold(saleStatus, id, statusId) {
    $.ajax({
        url: '/status',
        type: 'POST',
        data: {status: saleStatus, id: id, statusId: statusId},
        dataType: "text",
    }).done(function () {
        window.location.href = '/all-advertisements';
    }).fail(function (error) {
        console.log(error)
    });
}
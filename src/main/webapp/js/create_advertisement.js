$(document).ready(function () {
    $('#save-advert').click(function () {
        let dataFromForm = collectFormFields();
        let adverV = validateAdvertisementData(dataFromForm.advertisement);
        let carV = validateCarData(dataFromForm.car);
        let engineV = validateEngineData(dataFromForm.engine);
        let imageV = validateImage(dataFromForm.image);
        if (adverV && carV && engineV && imageV) {
            saveAdvertisement(dataFromForm);
        }
    });
});

function saveAdvertisement(data) {
    let commonData = new FormData();
    commonData.append("advert", JSON.stringify(data.advertisement));
    commonData.append("car", JSON.stringify(data.car));
    commonData.append("engine", JSON.stringify(data.engine));
    commonData.append("file", data.image);
    $.ajax({
        url: '/save-advertisement',
        type: 'POST',
        data: commonData,
        processData: false,
        contentType: false,
        dataType: "text"
    }).done(function (response) {
        response = JSON.parse(response);
        if (typeof response.url !== 'undefined') {
            window.location = response.url;
        }
    }).fail(function (err) {
        console.log(err);
    });
}

function collectFormFields() {
    let advertisement = {
        description: $('#description').val(),
        price: $('#price').val(),
    };

    let image = $("#image").prop('files')[0];

    let car = {
        name: $('#name').val(),
        yearOfIssue: $('#year').val(),
        color: $('#color').val(),
        kmAge: $('#km-age').val(),
        handlebar: $('#handlebar :selected').val(),
        transmission: $('#transmission :selected').val(),
        wheelDrive: $('#wheel-drive :selected').val(),
        carBody: $('#car-body :selected').val(),
    };

    let engine = {
        fuelType: $('#fuel-type :selected').val(),
        horsePower: $('#horse-power').val(),
    };

    return {
        advertisement: advertisement,
        engine: engine,
        car: car,
        image: image
    }
}

function validateAdvertisementData(data) {
    let result = false;
    let vDescription = descriptionValidate(data.description);
    let vPrice = priceValidate(data.price);
    if (vDescription && vPrice) {
        result = true;
    }
    return result;
}

function validateImage(image) {
    let result = false;
    let vImage = imageValidate(image);
    if (vImage) {
        result = true;
    }
    return result;
}

function validateCarData(car) {
    let result = false;
    let Vname = nameValidate(car.name);
    let Vcolor = colorValidate(car.color);
    let VkmAge = kmAgeValidate(car.kmAge);
    let Vyear = yearValidate(car.yearOfIssue);
    if (Vname && Vyear && Vcolor && VkmAge) {
        result = true;
    }
    return result;
}

function validateEngineData(engine) {
    let result = false;
    let VhorsePower = horsePowerValidate(engine.horsePower);
    if (VhorsePower) {
        result = true;
    }
    return result;
}

function horsePowerValidate(horsePower) {
    if (horsePower !== '' && horsePower.length >= 2 && horsePower.length <= 4) {
        if (isValidHorse(horsePower)) {
            addSuccessSpan('.horse-power', 'Л.с:');
            return true;
        } else {
            addErrorSpan('.horse-power', 'Ввести можно только целое число не более 5 чисел!');
            return false;
        }
    } else {
        addErrorSpan('.horse-power', 'Укажите колличество л.с!');
        return false;
    }

    function isValidHorse(horsePower) {
        let pattern = new RegExp(/^([0-9]+$)/);
        return pattern.test(horsePower);
    }
}

function yearValidate(year) {
    if (year !== '') {
        addSuccessSpan('.year-of-issue', 'Год выпуска: ');
        return true;
    } else {
        addErrorSpan('.year-of-issue', 'Год выпуска обязателен! ');
        return false;
    }
}

function kmAgeValidate(kmAge) {
    if (kmAge !== '') {
        if (kmAge.length >= 2 && kmAge.length <= 9 && isValidKmAge(kmAge)) {
            addSuccessSpan('.km-age', 'Укажите пробег или оставте поле пустым если авто новый:');
            return true;
        } else {
            addErrorSpan('.km-age', 'Вводить можно только числа и пробел не более 9ти символов!');
            return false;
        }
    } else {
        return true;
    }

    function isValidKmAge(kmAge) {
        let pattern = new RegExp(/^([0-9\s]+$)/);
        return pattern.test(kmAge);
    }
}

function colorValidate(color) {
    if (color !== '' && color.length >= 3) {
        addSuccessSpan('.color', 'Цвет:');
        return true;
    } else {
        addErrorSpan('.color', 'Укажите цвет авто!');
        return false;
    }
}

function nameValidate(name) {
    if (name !== '' && name.length >= 5 && name.length <= 30) {
        addSuccessSpan('.name', 'Название автомобиля:');
        return true;
    } else {
        addErrorSpan('.name','Поле обязательное и может содержать не более 30 символов!');
        return false;
    }
}

function imageValidate(image) {
    if (typeof image !== 'undefined') {
        addSuccessSpan('.image', 'Загрузите фото вашего авто:');
        return true;
    } else {
        addErrorSpan('.image', 'Необходимо загрузить фото вашего автомобиля!');
        return false;
    }
}

function priceValidate(price) {
    if (price !== '' && price.length >= 4 && price.length <= 12) {
        if (isValidPrice(price)) {
            addSuccessSpan('.price', 'Цена:');
            return true;
        } else {
            addErrorSpan('.price', 'Вводить можно только целые числа и пробел');
            return false;
        }
    } else {
        addErrorSpan('.price', 'Цена обязательное поле, колличество чисел не более 9!');
        return false;
    }

    function isValidPrice(price) {
        let pattern = new RegExp(/^([0-9\s]+$)/);
        return pattern.test(price);
    }
}

function descriptionValidate(description) {
    if (description !== '' && description.length >= 30) {
        addSuccessTextArea('.description', 'Описание:');
        return true;
    } else {
        addErrorTextArea('.description', 'Описание необходимо для оценки состояния автомобиля!');
        return false;
    }
}

function addSuccessSpan(nameClass, nameLabel) {
    $('' + nameClass + ' label:first').remove();
    $('' + nameClass + ' input:first').before("<label>" + nameLabel + "</label>");
    $('' + nameClass + ' span').remove();
    $('' + nameClass + '').removeClass('has-error').addClass('has-success');
    $('' + nameClass + ' input').after("<span class='form-control-feedback'></span>");
}

function addErrorSpan(nameClass, nameMessage) {
    $('' + nameClass + ' label:first').remove();
    $('' + nameClass + ' input:first').before("<label style='color: #ff4500'>" + nameMessage + "</label>");
    $('' + nameClass + ' span').remove();
    $('' + nameClass + '').removeClass('has-success').addClass('has-error')
        .append("<span class='form-control-feedback'></span>");
}

function addErrorTextArea(nameClass, nameMessage) {
    $('' + nameClass + ' label:first').remove();
    $('' + nameClass + ' textarea:first').before("<label style='color: #ff4500'>" + nameMessage + "</label>");
    $('' + nameClass + ' span').remove();
    $('' + nameClass + '').removeClass('has-success').addClass('has-error')
        .append("<span class='form-control-feedback'></span>");
}

function addSuccessTextArea(nameClass, nameLabel) {
    $('' + nameClass + ' label:first').remove();
    $('' + nameClass + ' textarea:first').before("<label>" + nameLabel + "</label>");
    $('' + nameClass + ' span').remove();
    $('' + nameClass + '').removeClass('has-error').addClass('has-success');
    $('' + nameClass + ' textarea').after("<span class='form-control-feedback'></span>");
}
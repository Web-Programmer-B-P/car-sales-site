$(document).ready(function () {
    singIn();
});

function checkUserData(user) {
    $.ajax({
        url: '/authorization',
        type: 'POST',
        data: {email: user.email, password: user.password},
        dataType: "text"
    }).done(function (response) {
        response = JSON.parse(response);
        if (typeof response.url !== 'undefined') {
            window.location = response.url;
        }
        if (typeof response.error !== 'undefined') {
            $('.error').remove();
            $('.email-in').before("<p class='common_label error' style='color: red'>" + response.error + "</p>");
        }
    }).fail(function (err) {
        console.log(err);
    });
}

function singIn() {
    $('.sing_in_page').click(function () {
        let email = $('#login').val();
        let password = $('#pwd').val();

        let vEmail = emailValidate(email);
        let vPassword = passwordValidate(password);

        if (vEmail && vPassword) {
            let userData = {
                email: email,
                password: password,
            };
            checkUserData(userData);
        }
    })
}

function emailValidate(email) {
    if (email !== '') {
        if (isValidEmailAddress(email)) {
            addSuccessSpan('.email-in', 'Email:');
            return true;
        } else {
            addErrorSpan('.email-in', 'Введите корректный почтовый адрес!');
            return false;
        }
    } else {
        addErrorSpan('.email-in', 'Поле обязательное!');
        return false;
    }

    function isValidEmailAddress(emailAddress) {
        let pattern = new RegExp(/^(("[\w-\s]+")|([\w-]+(?:\.[\w-]+)*)|("[\w-\s]+")([\w-]+(?:\.[\w-]+)*))(@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$)|(@\[?((25[0-5]\.|2[0-4][0-9]\.|1[0-9]{2}\.|[0-9]{1,2}\.))((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\.){2}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\]?$)/i);
        return pattern.test(emailAddress);
    }
}

function passwordValidate(password) {
    if (password !== '') {
        if (password.length >= 5) {
            addSuccessSpan('.pass-in', 'Password:');
            return true;
        } else {
            addErrorSpan('.pass-in', 'Пароль должен содержать 5 и более символов!');
            return false;
        }
    } else {
        addErrorSpan('.pass-in', 'Поле обязательное!');
        return false;
    }
}

function addSuccessSpan(nameClass, nameLabel) {
    $('' + nameClass + ' p:first').remove();
    $('' + nameClass + ' input:first').before("<p class='common_label'>" + nameLabel + "</p>");
    $('' + nameClass + ' span').remove();
    $('' + nameClass + '').removeClass('has-error').addClass('has-success');
    $('' + nameClass + ' input').after("<span class='form-control-feedback'></span>");
}

function addErrorSpan(nameClass, nameMessage) {
    $('' + nameClass + ' p:first').remove();
    $('' + nameClass + ' input:first').before("<p style='color: red' class='common_label'>" + nameMessage + "</p>");
    $('' + nameClass + ' span').remove();
    $('' + nameClass + '').removeClass('has-success').addClass('has-error')
        .append("<span class='form-control-feedback'></span>");
}
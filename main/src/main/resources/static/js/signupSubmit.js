function handleSubmit() {
    const body = {
        username: document.getElementById("username").value,
        password: document.getElementById("password").value
    }
    fetch("http://localhost:8080/signup", {
        method: "post",
        headers: {
            'Content-Type': "application/json;charset=UTF-8"
        },
        redirect: 'follow',
        body: JSON.stringify(body)
    }).then((response) => {
        if (response.redirected) {
            window.location.href = response.url;
        }
    });
}

const form = document.getElementById("submit");
form.addEventListener('click', handleSubmit);
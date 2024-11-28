var username = document.querySelector("#username");
var password = document.querySelector("#password");

const logar = () => {
    const myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    const raw = JSON.stringify({
        name: username.value,
        pass: password.value
    });

    const requestOptions = {
        method: "POST",
        headers: myHeaders,
        body: raw,
        redirect: "follow",
        credentials: "include" // Permite envio de cookies/autenticação com credenciais
    };

    fetch("http://localhost:8080/access/login", requestOptions)
        .then((response) => {
            if (response.status === 400) {
                return "erro";
            } else {
                return response.json();
            }
        })
        .then((result) => {
            if (result !== "erro") {
                sessionStorage.setItem("authToken", result.token);
                sessionStorage.setItem("name", username.value);
                sessionStorage.setItem("nivel", result.nivel);
                window.location.assign("../index.html");
            } else {
                alert("Usuário não encontrado");
            }
        })
        .catch((error) => alert("Credenciais incorreta!"));
};

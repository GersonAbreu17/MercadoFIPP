let categoria = $('#categorySelect');
let divAnuncios = $('#divAnuncios');

(function carregarCategorias() {
    categoria.html(""); // Limpa o elemento inicialmente
    const requestOptions = {
        method: "GET",
        headers: {
            "Authorization": `Bearer ${sessionStorage.getItem("authToken")}` // Envia o token corretamente
        },
        redirect: "follow"
    };

    fetch("http://localhost:8080/api/anuncios/get-many-categorias", requestOptions)
        .then((response) => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
        })
        .then((result) => {
            console.log(result);
            // Adiciona um item inicial
            let optionsHtml = `<option value="0" selected disabled hidden>Selecione</option>`;

            // Constrói o HTML das opções
            if (Array.isArray(result) && result.length > 0) {
                for (let element of result) {
                    optionsHtml += `<option value="${element.id}">${element.name.toUpperCase()}</option>`;
                }
            } else {
                optionsHtml += `<option value="" disabled>Nenhuma categoria encontrada</option>`;
            }

            // Atualiza o HTML do elemento de uma só vez
            categoria.html(optionsHtml);
        })
        .catch((error) => {
            console.error('Erro ao carregar categorias:', error);
            categoria.html(`<option value="" disabled>Erro ao carregar categorias</option>`);
        });
})()

carregarAnuncios();

function carregarAnuncios() {
    const myHeaders = new Headers();
    myHeaders.append("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG0iLCJpc3MiOiJsb2NhbGhvc3Q6ODA4MCIsIm5pdmVsIjoiMSIsImlhdCI6MTczMjE2NjU1OCwiZXhwIjoxNzMyMTY3NDU4fQ.FyPQDaKoSgLX7YZMsRE9bdQzDEmEbcX6qacWvjTV7YU");

    const raw = "";

    const requestOptions = {
        method: "GET",
        headers: myHeaders,
        body: raw,
        redirect: "follow"
    };

    fetch("http://localhost:8080/api/anuncios/anuncios", requestOptions)
        .then((response) => response.text())
        .then((result) => console.log(result))
        .catch((error) => console.error(error));
}
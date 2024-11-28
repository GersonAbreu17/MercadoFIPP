let categoria = $('#categorySelect');
let divAnuncios = $('#divAnuncios');
let searchInput = $("#searchInput");

(function carregarCategorias() {
    categoria.html(""); // Limpa o elemento inicialmente


    const myHeaders = new Headers();
    myHeaders.append("Authorization", "Bearer "+sessionStorage.getItem("authToken"));

    const requestOptions = {
    method: "GET",
    headers: myHeaders,
    redirect: "follow"
    };

    fetch("http://localhost:8080/api/user/anuncios/get-many-categorias", requestOptions)
        .then((response) => response.json())
        .then((result) => {
            console.log(result);
            // Adiciona um item inicial
            let optionsHtml = `<option value="0" selected>Todas</option>`;

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
    myHeaders.append("Authorization", "Bearer " + sessionStorage.getItem("authToken"));

    // Obtendo os valores de categoria e título
    const categoria_id = categoria.val() || "";
    const titulo_ad = searchInput.val() || "";

    // Construindo os parâmetros da URL
    const params = new URLSearchParams();
    if (categoria_id) params.append("catId", categoria_id);
    if (titulo_ad) params.append("titulo", titulo_ad);

    // Montando a URL com os parâmetros
    const url = `http://localhost:8080/api/user/anuncios/anunciosCategoria?${params.toString()}`;
    console.log(url);

    const requestOptions = {
        method: "GET",
        headers: myHeaders,
        redirect: "follow"
    };

    fetch(url, requestOptions)
        .then((response) => response.json())
        .then((result) => {
            console.log(result);
            let html = "";

            // Construindo o HTML para os anúncios
            for (const ad of result) {
                html += `
                <div style="cursor: pointer;" onclick="abrir_pagina(${ad.id})" class="mb-3 card p-5 container-fluid">
                    <h3 class="pb-3">${ad.titulo}</h3>
                `;
                if (ad.fotos.length > 0) {
                    html += `
                        <div class="divImagem">
                            <img src="../uploads/${ad.fotos[0]}" alt="Imagem do Anúncio">
                        </div>
                    `;
                }
                html += `</div>`;
            }

            // Atualizando o HTML da div
            divAnuncios.html(html);
        })
        .catch((error) => console.error("Erro ao carregar anúncios:", error));
}

function abrir_pagina(id){
    // alert(id);
    localStorage.setItem("ad", id);
    window.location.href = "pagina-anuncio/pagina.html";
}
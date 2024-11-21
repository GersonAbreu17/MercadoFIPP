let categoria = $('#categorySelect');
let divAnuncios = $('#divAnuncios');

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
            let optionsHtml = `<option value="0" selected hidden>Todas</option>`;

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


    const requestOptions = {
        method: "GET",
        headers: myHeaders,
        redirect: "follow"
    };

    fetch("http://localhost:8080/api/user/anuncios/anuncios", requestOptions)
        .then((response) => response.json())
        .then((result) => {
            console.log(result)
            let html = "";
            let i = 0;

            // Iterando sobre os resultados e limitando a 5 itens
            while (i < result.length) {
                
                let ad = result[i];

                html += `
                <div style="cursor: pointer;" onclick="abrir_pagina(${ad.id})" class="mb-3 card p-5 container-fluid">
                    <h3 class="pb-3">${ad.titulo}</h3>
                `;
                if(ad.fotos.length > 0)
                    html += `
                        <div class="divImagem">
                            <img src="../uploads/${ad.fotos[0]}" alt="">
                        </div>
                    `
                html += "</div>";                 
                i++;
            }

            // Atualizando o HTML da div
            divAnuncios.html(html);
        })
        .catch((error) => console.error(error));
}

function abrir_pagina(id){
    // alert(id);
    localStorage.setItem("ad", id);
    window.location.href = "pagina-anuncio/pagina.html";
}
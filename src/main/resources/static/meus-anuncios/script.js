let categoria = $('#categorySelect');
let divAnuncios = $('#divAnuncios');

carregarAnuncios();

function carregarAnuncios() {
    const myHeaders = new Headers();
    myHeaders.append("Authorization", "Bearer " + sessionStorage.getItem("authToken"));


    const requestOptions = {
        method: "GET",
        headers: myHeaders,
        redirect: "follow"
    };

    fetch("http://localhost:8080/api/vendedor/meusanuncios/anuncios?token="+sessionStorage.getItem("authToken"), requestOptions)
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
    window.location.href = "responder-perguntas/pagina.html";
}
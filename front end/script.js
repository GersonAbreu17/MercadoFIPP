let divAnuncios = $('#divAnuncios');

console.log(sessionStorage.getItem("authToken"));

(function carregarAnuncios() {
    const requestOptions = {
        method: "GET",
        headers: {
            "Authorization": `Bearer ${sessionStorage.getItem("authToken")}` // Corrigido para enviar o token no cabeçalho
        },
        redirect: "follow"
    };
    

    fetch("http://localhost:8080/inicio/ultimosAnuncios")
        .then((response) => response.json()) // Convertendo a resposta para JSON
        .then((result) => {
            console.log(result); // Verificar o formato dos dados recebidos
            let html = "";
            let i = 0;

            // Iterando sobre os resultados e limitando a 5 itens
            while (i < result.length && i < 5) {
                let ad = result[i];

                html += `
                <div class="card p-5 container-fluid">
                    <h3 class="pb-3">${ad.titulo}</h3>
                    <div class="divImagem">
                        <img src="${ad.fotos[0]}" alt="">
                    </div>
                </div>
                `;
                i++;
            }

            // Atualizando o HTML da div
            divAnuncios.html(html);
        })
        .catch((error) => console.error("Erro ao carregar anúncios:", error));
})();


function anunciar(){
    
}
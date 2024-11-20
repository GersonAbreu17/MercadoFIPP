let titulo = $("#titulo");
let data = $("#data");
let preco = $("#preco");
let categoria = $("#categoria");
let descricao = $("#descricao");
let fotos = $("#fotos");


function carregarData() {
    let date = new Date();
    let ano = date.getFullYear();
    let mes = date.getMonth() + 1;
    let dia = date.getDate();
    mes = mes < 10 ? '0' + mes : mes;
    dia = dia < 10 ? '0' + dia : dia;
    document.getElementById('data').value = `${ano}-${mes}-${dia}`;
};

function carregarCategorias() {
    categoria.html("");
    const requestOptions = {
        method: "GET",
        redirect: "follow"
    };

    fetch("http://localhost:8080/category/get-many", requestOptions)
        .then((response) => response.json())
        .then((result) => {
            console.log(result)
            categoria.html(`<option value="0" selected disabled hidden>Selecione</option>`);
            for(let element of result){
                categoria.html(categoria.html()+`
                    <option value="${element.id}">${element.name.toUpperCase()}</option>
                `)
            }
        })
        .catch((error) => console.error(error));
};

function adicionarAnuncio(){
    if(validarCampos())
    {
        console.log("Adicionado")
    }
}

function validarCampos() {
    // Limpar mensagens de erro antes de validar
    $(".pErro").text("");

    let isValid = true;

    // Validação do campo Título
    if (titulo.val().trim() === "") {
        $("#pErroTitulo").text("O campo Título é obrigatório.");
        isValid = false;
    }

    // Validação do campo Data (no exemplo fornecido, está desativado, mas considere a lógica se fosse necessário)
    if (data.is(":enabled") && data.val().trim() === "") {
        $("#pErroData").text("O campo Data é obrigatório.");
        isValid = false;
    }

    // Validação do campo Preço
    if (preco.val().trim() === "" || parseFloat(preco.val()) <= 0) {
        $("#pErroPreco").text("Insira um preço válido (maior que 0).");
        isValid = false;
    }

    // Validação do campo Categoria (checa se uma opção válida foi selecionada)
    if (categoria.val() == "0" || categoria.val() === null) {
        $("#pErroCategoria").text("Selecione uma categoria.");
        isValid = false;
    }

    // Validação do campo Descrição
    if (descricao.val().trim() === "") {
        $("#pErroDescricao").text("O campo Descrição é obrigatório.");
        isValid = false;
    }

    if(fotos[0].files.length > 3)
    {
        console.log($("#pErroFotos"))
        $("#pErroFotos").text("Você só pode enviar no máximo 3 fotos");
    }

    return isValid;
}


(function verificarAcesso(){

    let nivel = sessionStorage.getItem("nivel");
    try{
        console.log(nivel);
        if(nivel <= 2){
            console.log("Com permissao");
            (function inicializar(){
                carregarCategorias();
                carregarData();
            })()
        }
    } catch(e){
        alert(e);
    }
    console.log('a');

    
})()
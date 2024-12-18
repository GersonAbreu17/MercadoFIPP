let tbody = $("tbody");
let table = $("table");

let btnadicionar = $("#btnadicionar");
let btneditar = $("#btneditar");
let btnAbrirModalEditar = $("#btnAbrirModalEditar");
let btnexcluir = $("#btnexcluir");
let pesquisa = $("#pesquisa");
let btnpesquisa = $("#btnpesquisa");
let modalAdicionar = $("#modalAdicionar");
let addNome = $("#addNome");
let addNivel = $("#addNivel");
let addSenha = $("#addSenha");
let fecharAdd = $("#fecharAdd");
let modalEditar = $("#modalEditar");
let editarId = $("#editarId");
let editarNome = $("#editarNome");
let editarNivel = $("#editarNivel");
let editarSenha = $("#editarSenha");
let fecharEditar = $("#fecharEditar");

//inicializar
carregarTabela();


function carregarTabela() {
    const requestOptions = {
        method: "GET",
        headers: {
            "Authorization": `Bearer ${sessionStorage.getItem("authToken")}` // Corrigido para enviar o token no cabeçalho
        },
        redirect: "follow"
    };

    tbody.html('');

    fetch("http://localhost:8080/api/admin/usuarios/get-many?filtro=" + pesquisa.val(), requestOptions)
        .then((response) => response.json())
        .then((result) => {
            console.log(result)
            result.forEach((e) => {
                tbody.html(tbody.html() + `
                    <tr>
                        <td style="text-align: center;"> <label for="ck_${e.id}" style="width: 100%;"><input id="ck_${e.id}" input type="checkbox" data-id="${e.id}"/></label></td>
                        <td>${e.id}</td>
                        <td>${e.name}</td>
                        <td>${e.pass}</td>
                        <td>${e.level == 1 ? "Administrador" : e.level == 2 ? "Vendedor" : e.level == 3 ? "Usuário" : ""}</td>
                    </tr>
                `)
            });
        })
        .catch((error) => console.error(error));
}

function getSelecionados() {
    let lista = [];
    $('input[type="checkbox"]:checked').each(function () {
        lista.push($(this).data('id'));
    })
    return lista;
}

function deletar() {
    let promises = [];

    let selecionados = getSelecionados();
    if (selecionados.length > 0) {

        const requestOptions = {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${sessionStorage.getItem("authToken")}` // Corrigido para enviar o token no cabeçalho
            },
            redirect: "follow"
        };

        selecionados.forEach(id => {
            promises.push(new Promise((resolve) => {
                fetch("http://localhost:8080/api/admin/usuarios/delete?id=" + id, requestOptions)
                    .then((response) => response.text())
                    .then((result) => {
                        console.log(result);
                        resolve();
                    })
                    .catch((error) => {
                        console.error(error);
                        resolve();
                    });
            }));
        });
    } else {
        console.log("Não há o que deletar");
    }

    Promise.all(promises).then(carregarTabela);
}

function adicionar() {
    const myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");
    myHeaders.append("Authorization", `Bearer ${sessionStorage.getItem("authToken")}`);

    let raw;
    if(editarId.val().length == 0){
        raw = JSON.stringify({
            "name": addNome.val(),
            "pass": addSenha.val(),
            "level": addNivel.val()
        });
    }
    else{
        raw = JSON.stringify({
            "id": editarId.val(),
            "name": editarNome.val(),
            "pass": editarSenha.val(),
            "level": editarNivel.val()
        });
    }

    console.log(addNome.val());

    const requestOptions = {
        method: "POST",
        headers: myHeaders,
        body: raw,
        redirect: "follow"
    };

    fetch("http://localhost:8080/api/admin/usuarios/add", requestOptions)
        .then((response) => response.text())
        .then((result) => {
            console.log(result);
            fecharAdd.click();
            fecharEditar.click();
            limparCampos();
            carregarTabela();

        }).catch((error) => {
            console.error(error);
            fecharAdd.click();
            fecharEditar.click();
            limparCampos();
            carregarTabela();
        });
}

function abrirModalEditar() {
    let selecionados = getSelecionados();
    
    const myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");
    myHeaders.append("Authorization", `Bearer ${sessionStorage.getItem("authToken")}`);

    if(selecionados.length > 1)
        alert("Selecione somente um registro!");
    else if(selecionados.length < 1)
        alert("Selecione pelo menos um registro!");
    else{
        const requestOptions = {
            method: "GET",
            redirect: "follow",
            headers: myHeaders
        };

        fetch("http://localhost:8080/api/admin/usuarios/get-one?id=" + selecionados[0], requestOptions)
            .then((response) => response.json())
            .then((result) => {
                editarId.val(result.id);
                editarNome.val(result.name);
                editarSenha.val(result.pass);
                editarNivel.val(result.level);

                btnAbrirModalEditar.click();

            })
            .catch((error) => console.error(error));

    }
}

function limparCampos()
{
    addNome.val('');
    addNivel.val('');
    addSenha.val('');
    editarId.val('');
    editarNome.val('');
    editarNivel.val('');
    editarSenha.val('');
}
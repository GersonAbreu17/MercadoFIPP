let nivel = sessionStorage.getItem("nivel");

if(nivel == 1)
{
    $('.acesso-1').removeClass("d-none");
    $('.acesso-2').removeClass("d-none");
    $('.acesso-3').removeClass('d-none');
}
else if(nivel == 2)
{
    $('.acesso-2').removeClass("d-none");
    $('.acesso-3').removeClass('d-none');
}
else if(nivel == 3)
{
    $('.acesso-3').removeClass('d-none');
}
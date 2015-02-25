
/* - barraacessibilidade.js - */
// http://www.servicos.gov.br/portal_javascripts/barraacessibilidade.js?original=1
function setBaseFontColor(fontcolor,reset){console.log("dadad");if(reset){document.body.className=" ";createCookie("fontcolor",fontcolor,1)}document.body.className=fontcolor};function setStyleAc(){var fontcolor=readCookie("fontcolor");if(fontcolor!=null){setBaseFontColor(fontcolor,0)}else{setBaseFontColor("",1)}}
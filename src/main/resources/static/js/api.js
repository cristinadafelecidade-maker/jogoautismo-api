const API='';
function token(){return localStorage.getItem('token')||''}
function perfil(){return localStorage.getItem('perfil')||''}
function nomeUsuario(){return localStorage.getItem('nome')||''}
function encarregadoId(){return localStorage.getItem('encarregadoId')||''}
function alunoIdLogado(){return localStorage.getItem('alunoId')||''}
function authHeaders(){return {'Content-Type':'application/json','Authorization':'Bearer '+token()}}
function precisaLogin(){if(!token()){location.href='/login.html'}}
function exigePerfis(...perfisPermitidos){
    precisaLogin();
    const p=perfil();
    if(!perfisPermitidos.includes(p)){
        alert('Acesso negado para o perfil '+p+'.');
        redirecionarPorPerfil();
    }
}
function redirecionarPorPerfil(){
    const p=perfil();
    if(p==='ADMIN') location.href='/dashboard-admin.html';
    else if(p==='EDUCADOR') location.href='/atividades.html';
    else if(p==='ENCARREGADO') location.href='/sessoes.html';
    else if(p==='ALUNO') location.href='/jogos.html';
    else location.href='/login.html';
}
async function sair(){
    try{await fetch('/api/auth/logout',{method:'POST'});}catch(e){}
    localStorage.clear();
    location.href='/login.html';
}
async function api(path,opt={}){
    const o={...opt,headers:{...(opt.headers||{}),...(opt.body?authHeaders():{'Authorization':'Bearer '+token()})}};
    const r=await fetch(API+path,o);
    if(r.status===401||r.status===403){alert('Sessão expirada ou sem permissão. Faça login novamente.');await sair();return}
    if(r.status===204)return null;
    const txt=await r.text();let data=txt;
    try{data=txt?JSON.parse(txt):null}catch(e){}
    if(!r.ok)throw new Error(typeof data==='string'?data:'Erro na operação');
    return data;
}
function setUserInfo(){const el=document.getElementById('userInfo');if(el)el.textContent=`${nomeUsuario()} (${perfil()})`}
async function preencherSelect(id,path,labelFn){const sel=document.getElementById(id);if(!sel)return;const arr=await api(path);sel.innerHTML='<option value="">Selecione</option>'+arr.map(x=>`<option value="${x.id}">${labelFn(x)}</option>`).join('')}
function today(){return new Date().toISOString().slice(0,10)}

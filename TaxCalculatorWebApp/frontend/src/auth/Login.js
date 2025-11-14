import React, {useState} from 'react';
export default function Login({onSuccess}){
  const [email,setEmail]=useState('');
  const [password,setPassword]=useState('');
  const [err,setErr]=useState('');
  const submit = async (e) => {
    e.preventDefault(); setErr('');
    try {
      const res = await fetch((process.env.REACT_APP_API_URL || 'http://localhost:8080') + '/api/auth/login', {
        method:'POST', headers:{'Content-Type':'application/json'}, body: JSON.stringify({email,password})
      });
      if (!res.ok) { setErr('Login failed'); return; }
      const j = await res.json(); onSuccess(j.token);
    } catch(err){ setErr('Network error'); }
  };
  return (<form onSubmit={submit}><div><input placeholder='Email' value={email} onChange={e=>setEmail(e.target.value)} required /></div><div><input placeholder='Password' type='password' value={password} onChange={e=>setPassword(e.target.value)} required /></div><div><button type='submit'>Login</button></div>{err && <div style={{color:'red'}}>{err}</div>}</form>);
}

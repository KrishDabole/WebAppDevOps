import React, {useState, useEffect} from 'react';
export default function Calculator({token}) {
  const [gross,setGross]=useState('');
  const [variablePay,setVariablePay]=useState('');
  const [nps,setNps]=useState('');
  const [fy,setFy]=useState('2025-2026');
  const [result,setResult]=useState(null);
  const [history,setHistory]=useState([]);
  const [showDetails,setShowDetails]=useState(false);
  const apiBase = (process.env.REACT_APP_API_URL || 'http://localhost:8080');
  const submit = async (e)=> {
    e && e.preventDefault();
    const body = { grossTotalPackage: Number(gross||0), variablePay: Number(variablePay||0), npsContribution: Number(nps||0), financialYearStr: fy };
    const res = await fetch(apiBase + '/api/calculator/submit', { method:'POST', headers: {'Content-Type':'application/json','Authorization':'Bearer '+token}, body: JSON.stringify(body) });
    if(res.ok){ const j = await res.json(); setResult(j); loadHistory(); } else { alert('Submit failed'); }
  };
  const loadHistory = async ()=> {
    const res = await fetch(apiBase + '/api/calculator/history', { method:'GET', headers:{'Authorization':'Bearer '+token} });
    if(res.ok){ const j = await res.json(); setHistory(j); }
  };
  useEffect(()=>{ loadHistory(); }, []);
  return (<div>
    <h3>Calculator</h3>
    <form onSubmit={submit} style={{display:'grid',gridTemplateColumns:'1fr 1fr',gap:10,maxWidth:700}}>
      <div><label>Gross Total Package:</label><br/><input value={gross} onChange={e=>setGross(e.target.value)} /></div>
      <div><label>Variable Pay:</label><br/><input value={variablePay} onChange={e=>setVariablePay(e.target.value)} /></div>
      <div><label>NPS Contribution:</label><br/><input value={nps} onChange={e=>setNps(e.target.value)} /></div>
      <div><label>Financial Year:</label><br/><select value={fy} onChange={e=>setFy(e.target.value)}><option>2024-2025</option><option>2025-2026</option><option>2026-2027</option></select></div>
      <div style={{gridColumn:'1 / -1'}}><button type='submit'>Calculate & Save</button><button type='button' onClick={()=>setShowDetails(s=>!s)}>Toggle Details</button></div>
    </form>
    {result && <div style={{marginTop:10}}>
      <h4>Result (summary)</h4>
      <table style={{borderCollapse:'collapse',width:'100%',maxWidth:700}}>
        <tbody>
          <tr><td style={{border:'1px solid #ddd',padding:6}}>Taxable Income</td><td style={{border:'1px solid #ddd',padding:6}}>{result.C30}</td></tr>
          <tr><td style={{border:'1px solid #ddd',padding:6}}>New-regime Total Tax (yearly)</td><td style={{border:'1px solid #ddd',padding:6}}>{result.new_C37}</td></tr>
          <tr><td style={{border:'1px solid #ddd',padding:6}}>Old-regime Total Tax (yearly)</td><td style={{border:'1px solid #ddd',padding:6}}>{result.old_C37}</td></tr>
        </tbody>
      </table>
      {showDetails && <div style={{marginTop:10}}><pre style={{background:'#f7f7f7',padding:10}}>{JSON.stringify(result,null,2)}</pre></div>}
    </div>}
    <div style={{marginTop:20}}>
      <h4>Submission history</h4>
      <ul>{history.map(h=>(<li key={h.id}>{new Date(h.createdAt).toLocaleString()} - Input: {h.inputJson} - Result: {h.resultJson}</li>))}</ul>
    </div>
  </div>);
}

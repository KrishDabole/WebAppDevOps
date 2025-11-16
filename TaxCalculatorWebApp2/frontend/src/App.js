import React, { useState } from 'react';
import Calculator from './Calculator';
function App(){ const [token,setToken]=useState('dummy'); return (<div style={{padding:20}}>{token? <Calculator token={token}/> : <div>Login</div>}</div>);}
export default App;

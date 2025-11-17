import React, { useState } from 'react';
import { Table, TableBody, TableCell, TableRow, Button, TextField } from '@mui/material';
export default function TaxCalculator() {
  const [inputs, setInputs] = useState({ totalPackage: '', variablePay: '', nps: '' });
  const [result, setResult] = useState(null);
  const handleChange = (e) => setInputs({ ...inputs, [e.target.name]: e.target.value });
  const calculate = async () => {
    const res = await fetch('/api/calculateFull', { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(inputs) });
    const data = await res.json();
    setResult(data);
  };
  return (
    <div style={{ padding: '20px' }}>
      <h2>Tax Calculator</h2>
      <TextField name='totalPackage' label='Total Package' onChange={handleChange} />
      <TextField name='variablePay' label='Variable Pay' onChange={handleChange} />
      <TextField name='nps' label='NPS Contribution' onChange={handleChange} />
      <Button variant='contained' onClick={calculate}>Calculate</Button>
      {result && (
        <div>
          <h3>Earnings</h3>
          <Table><TableBody>{Object.entries(result.earnings).map(([k,v]) => (<TableRow key={k}><TableCell>{k}</TableCell><TableCell>{v}</TableCell></TableRow>))}</TableBody></Table>
          <h3>Deductions</h3>
          <Table><TableBody>{Object.entries(result.deductions).map(([k,v]) => (<TableRow key={k}><TableCell>{k}</TableCell><TableCell>{v}</TableCell></TableRow>))}</TableBody></Table>
          <h3>Summary</h3>
          <p>Gross Salary: {result.grossSalary}</p>
          <p>Taxable Income: {result.taxableIncome}</p>
          <p>Old Regime Tax: {result.oldRegimeTax}</p>
          <p>New Regime Tax: {result.newRegimeTax}</p>
          <p>Monthly Gross: {result.monthlyGross}</p>
          <p>Monthly Old Tax: {result.monthlyOldTax}</p>
          <p>Monthly New Tax: {result.monthlyNewTax}</p>
        </div>
      )}
    </div>
  );
}
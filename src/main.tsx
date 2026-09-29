import React from 'react';
import ReactDOM from 'react-dom/client';
import { App } from './App';
import { RentifyProvider } from './context/RentifyContext';
import './index.css';

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <RentifyProvider>
      <App />
    </RentifyProvider>
  </React.StrictMode>
);

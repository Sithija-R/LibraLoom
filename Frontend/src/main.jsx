import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import './index.css'
import { BrowserRouter } from "react-router-dom";
import ContextProvider from './context/ContextProvider.jsx';
import logger from './services/logService.js'
import { Provider } from 'react-redux';
import { store } from '../Storage/Storage.jsx';

logger.init();


ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <BrowserRouter>
   
      <ContextProvider>
      <Provider store={store}>
        
        <App />
      </Provider>
      </ContextProvider>
    </BrowserRouter>
  </React.StrictMode>
)

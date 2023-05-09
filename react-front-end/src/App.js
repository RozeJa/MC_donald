import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Home from './pages/Home';
import OrderPanel from './pages/OrderPanel';
import PreparationPanel from './pages/PreparationPanel';
import DeliveryPanel from './pages/DeliveryPanel';
import Error from './pages/Error';
import CategoryPanel from './pages/CategoryPanel';
import OrderProvider from './OrderProvider';
import ProductsPanel from './pages/ProductsPanel';
import Product from './pages/Product';
import { useEffect, useState } from 'react';

function App() {

  const initOrder = {
    products: [],
    finished: false
  }

  useEffect(() => {
    if (sessionStorage.getItem('order') === null) 
      sessionStorage.setItem('order', JSON.stringify(initOrder))
  }, [])

  return (
      <BrowserRouter>
        <Routes>
          <Route index element={ <Home /> }/>

          <Route path="/order" element={ <OrderPanel /> }/>
          <Route path="/order/categories/" element={ < CategoryPanel /> }/>
          <Route path="/order/products/:categoryId" element={ < ProductsPanel /> }/>
          <Route path="/order/addProduct/:productId" element={ <Product /> }/>
          <Route path='/order/confirrm' element={ /*todo*/ <></>} />
          <Route path="/preparation" element={ <PreparationPanel /> }/>
          <Route path="/delivery" element={ <DeliveryPanel /> }/>

          <Route path="*" element={ <Error code="404" call={"Not Found"} /> } />

        </Routes>
      </BrowserRouter>      

  );
}

export default App;

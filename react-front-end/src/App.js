import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Home from './pages/Home';
import OrderPanel from './pages/OrderPanel';
import PreparationPanel from './pages/PreparationPanel';
import DeliveryPanel from './pages/DeliveryPanel';
import Error from './pages/Error';
import OrderProvider from './OrderProvider';
import { useEffect, useState } from 'react';

function App() {

  const initOrder = {
    products: [],
    finished: false
  }

  const [order, setOrder] = useState(initOrder)
  const [refresh, setRefresh] = useState(true)

  useEffect(() => {
    console.log(order);
  }, [order])

  return (
    <OrderProvider.Provider value={{order, setOrder, refresh, setRefresh, initOrder}}>
      <BrowserRouter>
        <Routes>
          <Route index element={ <Home /> }/>

          <Route path="/order" element={ <OrderPanel /> }/>
          <Route path="/preparation" element={ <PreparationPanel /> }/>
          <Route path="/delivery" element={ <DeliveryPanel /> }/>

          <Route path="*" element={ <Error code="404" call={"Not Found"} /> } />

        </Routes>
      </BrowserRouter>      
    </OrderProvider.Provider>

  );
}

export default App;

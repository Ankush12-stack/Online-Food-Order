// import './App.css';
import { ThemeProvider } from '@emotion/react';
import { darkTheme } from './Theme/DarkTheme';
import { CssBaseline } from '@mui/material';
import { CustomerRoute } from './Routers/CustomerRoute';
import { useDispatch, useSelector } from 'react-redux';
import { getUser } from './component/State/Authentication/Action';
import { store } from './component/State/store';
import { useEffect } from 'react';




function App() {
  const dispatch = useDispatch();
  const jwt=localStorage.getItem("jwt");
  const { auth } = useSelector((store) => store);

  useEffect(()=>{
dispatch(getUser(auth.jwt || jwt));
  },[dispatch,auth.jwt, jwt]);

  return (
    <ThemeProvider theme={darkTheme}>
      <CssBaseline/>
<CustomerRoute/>
    </ThemeProvider>
  );
}

export default App;

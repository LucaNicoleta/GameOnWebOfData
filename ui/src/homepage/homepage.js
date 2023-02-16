
import * as React from 'react';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import Button from '@mui/material/Button';
import { useNavigate } from "react-router-dom";
import VisibilityOff from '@mui/icons-material/VisibilityOff';
import Visibility from '@mui/icons-material/Visibility';
import { IconButton, FormControl, InputAdornment, InputLabel, Input, Typography } from '@mui/material';
import './homepage.css';
import AuthContext from '../context/AuthContext'
import SceneContext from '../context/SceneContext'

import { bookApi } from '../misc/BookApi'
import { handleLogError } from '../misc/Helpers'
export default function Homepage() {
    const history = useNavigate();
  const [showPassword, setShowPassword] = React.useState(false);
  const [action, setAction] = React.useState("LOGIN");
  const [username, setUsername] = React.useState('');
  const [password, setPassword] = React.useState('');
  const handleClickShowPassword = () => setShowPassword((show) => !show);
  const handleSetUsername = (event) => setUsername(event.target.value);
  const handleSetPassword = (event) => setPassword(event.target.value);
  const [hasGameSession, setGameSession] = React.useState(false);
  const [isAuth, setIsAuth] = React.useState(false);
  const Auth =React.useContext(AuthContext);
  const Scene =React.useContext(SceneContext);
  const doLogin = (username, password) => {
    bookApi.authenticate(username, password)
      .then(response => {
        console.log(response.data);
        const authdata = window.btoa(username + ':' + password)
        const user = { username, password, authdata }
        
        
        Auth.userLogin(user)

        setIsAuth(true);
      })
      .catch(error => {
        handleLogError(error)
        this.setState({ isError: true })
      })
 
  
    
  
  }

  function doRegister(username, password){
    const xhr = new XMLHttpRequest();
    xhr.open("POST", "http://localhost:8080/auth/signup");
    xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
    
    const body = JSON.stringify({
      username: username,
      password: password
    });
  
    xhr.onload = () => {
      if (xhr.readyState === 4 && (xhr.status === 201||xhr.status===200)) {
        {console.log(xhr.responseText);
        setAction("LOGIN");}
      } else {
        console.log("Error: " + xhr.response)
      }
    };
    xhr.send(body);
  }
  function doLogout(username, password){
    console.log(Auth.userIsAuthenticated);
    Scene.setScene(null)
    Auth.userLogout()
    setIsAuth(false);
  } 
  React.useEffect(() => {
 
  console.log(Auth);
  setIsAuth( Auth.userIsAuthenticated());

// empty dependency array means this effect will only run once (like componentDidMount in classes)
}, []);

    const user = Auth.getUser();
  const startGame = ()=>{
    console.log(user);
    bookApi.startGame(user)
    .then(response => {
      console.log(response.data );
      Scene.setScene(response.data);
      history('/play',{state:{currentScene:`${JSON.stringify(response.data)}`}})
    })
    .catch(error => {
      handleLogError(error)
    })
  }
  const continue_game= ()=>{
    console.log(user);
    bookApi.loadGame(user)
    .then(response => {
      console.log(response.data );
      Scene.setScene(response.data);
      history('/play',{state:{currentScene: `${JSON.stringify(response.data)}`}})
    })
    .catch(error => {
      handleLogError(error)
    })
  }
  //getGameSession();
  const handleMouseDownPassword = (event) => {
    event.preventDefault();
  };
  const authenticated =  (

    <Grid item  paddingTop={'0.5%'} height='100%'>
        <Grid container justifyContent={'center'} width='100%' marginBottom={'5%'}>
            <Grid item xs={3} display={'flex'} justifyContent='end'>
                <Box className='login-tab-text'  >
.
                </Box>
            </Grid>
            <Grid item xs={6} >
                <Box className='div-with-gradient-border' padding={'8px'} alignContent={'flex-start'} justifyContent='center' display='flex' flexDirection={'column'} height={'100%'} width='90%' margin='auto' bgcolor={'antiquewhite'} borderRadius='4%'>
                    <Grid container spacing={2}>
                        <Grid item xs={10}><Box sx={{ width:'100%',marginLeft: '12px', paddingLeft: '2%', backgroundImage: `url(${'./logo_game.png'})`, backgroundSize: '100% 100%', width: '50%', margin: 'auto', height: '100%' }}></Box>
                        </Grid>
                        <Grid item xs={10}><Typography>
                            Welcome
                        </Typography></Grid>
                        <Grid item xs={10}><Box display='flex' direction='column'>

                            <Button onClick={()=>doLogout(username, password)} variant='contained' sx={{ bgcolor: 'brown', width: '40%', alignSelf: 'end', marginRight: '16px', marginTop: '28px' }}>Logout</Button>
                            <Button onClick={()=>startGame()} variant='contained' sx={{ bgcolor: 'brown', width: '40%', alignSelf: 'end', marginRight: '16px', marginTop: '28px' }}>Start new game</Button>
                            <Button onClick={continue_game}variant='contained' sx={{ bgcolor: 'brown', width: '40%', alignSelf: 'end', marginRight: '16px', marginTop: '28px' }}>Load saved game</Button>
                        </Box></Grid>
                        
                    </Grid>

                </Box>
                {/*  */}
            </Grid>
            <Grid item xs={3} display={'flex'} justifyContent='start'>
                <Box className='login-tab-text'>
.
                </Box>
            </Grid>
        </Grid>

    </Grid>

);
const unauthenticated=(<Grid item xs={5} paddingTop={'0.5%'}>
<Grid container justifyContent={'center'} width='100%' marginBottom={'5%'}>
  <Grid item xs={3} display={'flex'} justifyContent='end'>
    <Box onClick={() => setAction("LOGIN")} className='login-tab-text' sx={{borderStyle: action==="LOGIN"?'solid':''}}>
      Login
    </Box>
  </Grid>
  <Grid item xs={6}>
    <Box className='div-with-gradient-border' padding={'8px'} alignContent={'flex-start'} justifyContent='center' display='flex' flexDirection={'column'} height={'100%'} width='90%' margin='auto' bgcolor={'antiquewhite'} borderRadius='4%'>
      <Box display='flex' direction='row' >
        <Box display='flex' flexDirection='column'>
          <FormControl sx={{ m: 1, width: '25ch', marginLeft: '16px', marginTop: '16px', }} variant="standard">
            <InputLabel sx={{ color: 'brown', fontWeight: 'bolder' }} htmlFor="username-register" >Username</InputLabel>
            <Input
              // username input
              onChange={handleSetUsername}
              id="username-register"
              type='text'
            />
          </FormControl>
          <FormControl sx={{ m: 1, width: '25ch', marginLeft: '16px', }} variant="standard">
            <InputLabel sx={{ color: 'brown', fontWeight: 'bolder' }} htmlFor="standard-adornment-password">Password</InputLabel>
            <Input
              id="standard-adornment-password"
              type={showPassword ? 'text' : 'password'}
              // password input
              onChange={handleSetPassword}
              endAdornment={
                <InputAdornment position="end">
                  <IconButton 
                    aria-label="toggle password visibility"
                    onClick={handleClickShowPassword}
                    onMouseDown={handleMouseDownPassword}
                  >
                    {showPassword ? <VisibilityOff sx={{ color: 'brown' }} /> : <Visibility sx={{ color: 'brown' }} />}
                  </IconButton>
                </InputAdornment>
              }
            />
          </FormControl>
        </Box>
        <Box sx={{ marginLeft: '12px', paddingLeft: '2%', backgroundImage: `url(${'./logo_game.png'})`, backgroundSize: '100% 100%', width: '50%', margin: 'auto', height: '100%' }}></Box>
      </Box>

      <Button type='submit' onClick={() => {
        action === "LOGIN" ? doLogin(username, password) : doRegister(username, password)
        }} variant='contained' sx={{ bgcolor: 'brown', width: '40%', alignSelf: 'end', marginRight: '16px', marginTop: '28px' }}>{action === "LOGIN" ? "Login" : "Register"}</Button>
    </Box>
    {/*  */}
  </Grid>
  <Grid item xs={3} display={'flex'} justifyContent='start'>
    <Box className='login-tab-text' sx={{backdropFilter: action==="REGISTER"?'solid':''

    }} onClick={() => setAction("REGISTER")}>
      Register
    </Box>
  </Grid>
</Grid>

</Grid>
);


  return (
    <Box className="root-content">
      <Grid container direction={'column'} rowGap={0.2} justifyContent='space-around' alignItems={'space-around'}>
        <Grid item xs={1} height={'100%'} sx={{ marginTop: '2%' }}>
          <Grid container height={'100%'}>
            <Grid item xs={3} display={'flex'} justifyContent='end'>
              <Box className='square' bgcolor='#d50000' sx={{ borderColor: '#d50000' }}></Box>
            </Grid>
            <Grid item xs={6} display='flex' alignItems={'center'}><hr></hr></Grid>
            <Grid item xs={3} display={'flex'} justifyContent='start'>
              <Box className='square' bgcolor='#f0fe2f' sx={{ borderColor: '#f0fe2f' }}></Box>
            </Grid>
          </Grid>

        </Grid>
        {
            isAuth?(authenticated):(unauthenticated)
        }
        <Grid item xs={1}>
          <Grid container>
            <Grid item xs={3} display={'flex'} justifyContent='end'>
              <Box className='square' bgcolor='#006eff' sx={{ borderColor: '#006eff', borderStyle: 'double' }} ></Box>
            </Grid>
            <Grid item xs={6} display='flex' alignItems={'center'}><hr></hr></Grid>

            <Grid item xs={3} display={'flex'} justifyContent='start'>
              <Box className='square' bgcolor='#00bd26' sx={{ borderColor: '#00bd26' }}></Box>
            </Grid>


          </Grid>
        </Grid>

      </Grid>

    </Box>
  );
}





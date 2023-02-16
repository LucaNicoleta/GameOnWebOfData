
import * as React from 'react';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import Button from '@mui/material/Button';
import VisibilityOff from '@mui/icons-material/VisibilityOff';
import Visibility from '@mui/icons-material/Visibility';
import { IconButton, FormControl, InputAdornment, InputLabel, Input } from '@mui/material';


export default function Unauthenticated() {
  const [showPassword, setShowPassword] = React.useState(false);
  const [action, setAction] = React.useState("LOGIN");
  const [username, setUsername] = React.useState('');
  const [password, setPassword] = React.useState('');
  const handleClickShowPassword = () => setShowPassword((show) => !show);
  const handleSetUsername = (event) => setUsername(event.target.value);
  const handleSetPassword = (event) => setPassword(event.target.value);

  const handleMouseDownPassword = (event) => {
    event.preventDefault();
  };

  const doLogin = (username, password) => {
    const xhr = new XMLHttpRequest();
    xhr.open("POST", "http://localhost:8080/login");
    var data = new FormData();
    data.append('username', username);
    data.append('password', password);
  
    console.log("username: " + username);
    console.log("pass: " + password);
  
    xhr.onload = function(){
      if (xhr.readyState === 4 && xhr.status === 200) {
        console.log(xhr.responseText);
        window.location.href = '/play';
      } else {
        console.log("Error: " + xhr.response);
      }
    };
    xhr.send(data);
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
      if (xhr.readyState === 4 && xhr.status === 201) {
        console.log(xhr.responseText);
      } else {
        console.log("Error: " + xhr.response)
      }
    };
    xhr.send(body);
  }

  return (

        <Grid item xs={5} paddingTop={'0.5%'}>
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
}





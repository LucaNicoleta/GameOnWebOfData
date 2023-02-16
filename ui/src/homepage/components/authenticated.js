
import * as React from 'react';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import Button from '@mui/material/Button';
import VisibilityOff from '@mui/icons-material/VisibilityOff';
import Visibility from '@mui/icons-material/Visibility';
import { IconButton, FormControl, InputAdornment, InputLabel, Input, Typography } from '@mui/material';


export default function Authenticated() {
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



    function doLogout(username, password){
      const xhr = new XMLHttpRequest();
      xhr.open("POST", "http://localhost:8080/auth/logout");
      xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
      
      const body = JSON.stringify({
        username: username,
        password: password
      });
    
      xhr.onload = () => {
        if (xhr.readyState === 4 && xhr.status === 200) {
          console.log(xhr.responseText);
        } else {
          console.log("Error: " + xhr.response)
        }
      };
      xhr.send(body);
    }
  
    return (

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

                                <Button  variant='contained' sx={{ bgcolor: 'brown', width: '40%', alignSelf: 'end', marginRight: '16px', marginTop: '28px' }}>Logout</Button>
                                <Button variant='contained' sx={{ bgcolor: 'brown', width: '40%', alignSelf: 'end', marginRight: '16px', marginTop: '28px' }}>Start new game</Button>
                                <Button variant='contained' sx={{ bgcolor: 'brown', width: '40%', alignSelf: 'end', marginRight: '16px', marginTop: '28px' }}>Load saved game</Button>
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
}





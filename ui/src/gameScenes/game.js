import * as React from 'react';
import { styled } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import Button from '@mui/material/Button';
import CardMedia from '@mui/material/CardMedia';
import HelpIcon from '@mui/icons-material/Help';
import ConstructionIcon from '@mui/icons-material/Construction';
import MenuBookIcon from '@mui/icons-material/MenuBook';
import RestartAltIcon from '@mui/icons-material/RestartAlt';
import { IconButton, Typography } from '@mui/material';
import { bookApi } from '../misc/BookApi'
import AuthContext from '../context/AuthContext'
import SceneContext from '../context/SceneContext'

import { useNavigate } from "react-router-dom";
import './game.css';
const Item = styled(Paper)(({ theme }) => ({
  background: 'none',
  ...theme.typography.body2,
  //padding: theme.spacing(1),
  textAlign: 'center',
  color: theme.palette.text.secondary,
  height: '100%'
}));

export default function GameScene() {
  const Scene = React.useContext(SceneContext);
  const scene_ = Scene.getScene();

  const history = useNavigate();
  const [scene, setScene] = React.useState(scene_);


  const [optionChosen, setOption] = React.useState('A');
  const Auth = React.useContext(AuthContext);
  const user = Auth.getUser()
  const readInventory =()=>{
    bookApi.readInventory(user).then(response => {
      console.log(response.data );
      history('/inventory',{state:{inv:`${JSON.stringify(response.data)}`}})
      
    }).catch(error=>{
      console.log("Error:"+error)
    })
  }
  const nextScene = (option) => {

    console.log(user)
    const nextBody = {
      currentSceneResIdentifier: scene.identifier,

      playerResIdentifier: user.username,
      chosenOption: option
    }
    console.log(nextBody);
    bookApi.nextScene(user, nextBody).then(response => {

      console.log(response.data);

      setScene(response.data)
      Scene.setScene(response.data);
      console.log(scene.backgroundImage);
    })
      .catch(error => {
        console.log(error)
        //handleLogError(error)
      })
  }
  const options = scene.options;
  return (
    <Box sx={{ flexGrow: 1, height: '100%', backgroundImage: `url(${'https://ambicular.com/skuawk-download/space/patrick-mcmanaman.jpg'})`, backgroundRepeat: "no-repeat", backgroundSize: "100% 100%" }}>
      <Grid sx={{ height: '100%' }} container spacing={0} justifyContent='space-around'>
        <Grid sx={{ height: '100%' }} item xs={1.5}>
          <Item sx={{
            height: '100%',
          }} >
            <Grid sx={{ height: '100%' }} justifyContent="space-between" direction={"column"} container spacing={0}>
              <Grid item xs={2} sx={{ background: 'radial-gradient(circle, rgba(0,194,255,1) 12%, rgba(9,75,121,1) 100%)', clipPath: 'polygon(0% 0%, 100% 0%, 75% 100%, 25% 100%)', border: '2px solid white' }}>
                <IconButton size="large" color='white'>
                  <HelpIcon sx={{ height: '3rem!important', width: '3rem!important', color: 'white' }} />
                </IconButton>
              </Grid>
              <Grid item xs={8} sx={{ background: 'radial-gradient(circle, rgba(0,194,255,1) 12%, rgba(9,75,121,1) 100%)', clipPath: 'polygon(25% 0%, 75% 0%, 95% 50%, 75% 100%, 25% 100%, 5% 50%)', border: '1px solid', borderRadius: '25%' }}></Grid>

              <Grid item xs={2} sx={{ background: 'radial-gradient(circle, rgba(0,194,255,1) 12%, rgba(9,75,121,1) 100%)', clipPath: 'polygon(25% 0%, 75% 0%, 100% 100%, 0% 100%)', border: '2px solid white' }}>
                <IconButton>
                  <MenuBookIcon onClick={()=>readInventory()} sx={{ height: '3rem!important', width: '3rem!important', color: 'white' }} />
                </IconButton >
              </Grid>
            </Grid>
          </Item>
        </Grid>
        <Grid sx={{ height: '100%', borderRadius: '50px', backgroundImage: `url(${'https://img.freepik.com/free-photo/white-painted-wall-texture-background_53876-138197.jpg?w=900&t=st=1674471432~exp=1674472032~hmac=4e380918d7d2a38a80fc0208bf8fe0c51663d2c223a28fc6ecbd59745f253c7d'})`, backgroundRepeat: "no-repeat", backgroundSize: "100% 100%" }} item xs={8}>

          <Box sx={{ height: '100%', marginTop: '10px', }} >
            <Grid sx={{ height: '100%' }} direction={"column"} justifyContent='space-around' container spacing={0.1} rowGap={0.2}>
              {(scene.backgroundImage && scene.backgroundImage.length !== 0) && (<Grid item xs={6}>

                <CardMedia
                  sx={{ height: '100%', borderRadius: '50px', border: '4px double white ', backgroundSize: '100% 100%', width: '90%', margin: 'auto' }}
                  image={require('../images' + scene.backgroundImage)}
                />

              </Grid>)}

              <Grid item xs={2} marginTop='0px' display='flex' flexDirection={'column'} alignItems='center' justifyContent='center'>
                <Typography width={'25%'} fontSize={"1.5vw"} fontWeight={'bold'} textAlign="center" borderBottom='3px solid brown' color='rgba(9,75,121,1)' fontFamily={'MedievalSharp'}>
                  {scene.marvel ? scene.marvel.substring(11) : "Narator"}
                </Typography>
                <Typography className='corner-border' width={'90%'} margin='auto' fontSize={"1.2vw"} fontStyle='italic' fontWeight={'bold'} textAlign="center" fontFamily={'MedievalSharp'}>
                  {scene.text}
                </Typography>

              </Grid>

              {scene.type === "ACTIVE" ? (
                <Grid item xs={scene.backgroundImage.length === 0 ? 7 : 2} height={'100%'}>
                  <Grid alignContent={'space-around'} justifyContent={'space-around'} container columnSpacing={2} rowSpacing={1} height={'100%'}>
                    {scene.backgroundImage.length === 0 ? options.map((o) => (
                      <Grid key={o.optValue} item xs={5} height='50%'>
                        <CardMedia onClick={() => { nextScene(o.optValue) }}
                          sx={{ height: '100%' }}
                          image={o.content[0] == '/' ? require('../images' + o.content) : o.content}
                        />
                      </Grid>)) : options.map((o) => (
                        <Grid key={o.optValue} item xs={5}>
                          <Button onClick={() => { nextScene(o.optValue) }} variant='contained' className='option-button'>{o.content}</Button>
                        </Grid>))
                    }
                    {/*
                    <Grid item xs={5}>
                    <Button  variant='contained' className='option-button'>B.</Button>
                    </Grid>
                    <Grid item xs={5}>
                    <Button  variant='contained' className='option-button'>C.</Button>
                    </Grid>
                    <Grid item xs={5}>
                    <Button  variant='contained' className='option-button'>D.</Button>
                </Grid>*/}
                  </Grid>


                </Grid>) : (<Button onClick={() => nextScene("")} variant='contained' className='option-button' sx={{ width: '20%', alignSelf: 'end', marginRight: '6%' }}>Next</Button>
              )}

            </Grid></Box>


        </Grid>
        <Grid sx={{ height: '100%' }} item xs={1.5}>
          <Item sx={{
            height: '100%',
          }} >
            <Grid sx={{ height: '100%' }} justifyContent="space-between" direction={"column"} container spacing={0}>
              <Grid item xs={2} sx={{ background: 'radial-gradient(circle, rgba(0,194,255,1) 12%, rgba(9,75,121,1) 100%)', clipPath: 'polygon(0% 0%, 100% 0%, 75% 100%, 25% 100%)', border: '2px solid white' }}>
                <IconButton size="large">
                  <ConstructionIcon sx={{ height: '3rem!important', width: '3rem!important', color: 'white' }} />
                </IconButton>
              </Grid>
              <Grid item xs={8} sx={{ background: 'radial-gradient(circle, rgba(0,194,255,1) 12%, rgba(9,75,121,1) 100%)', clipPath: 'polygon(25% 0%, 75% 0%, 95% 50%, 75% 100%, 25% 100%, 5% 50%)', border: '1px solid', borderRadius: '25%' }}></Grid>

              <Grid item xs={2} sx={{ background: 'radial-gradient(circle, rgba(0,194,255,1) 12%, rgba(9,75,121,1) 100%)', clipPath: 'polygon(25% 0%, 75% 0%, 100% 100%, 0% 100%)', border: '2px solid white' }}>
                <IconButton>
                  <RestartAltIcon sx={{ height: '3rem!important', width: '3rem!important', color: 'white' }} />
                </IconButton >
              </Grid>
            </Grid>
          </Item>
        </Grid>

      </Grid>
    </Box>
  );
}

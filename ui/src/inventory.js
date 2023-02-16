import { bookApi } from './misc/BookApi'
import * as React from 'react';
import AuthContext from './context/AuthContext'
import { useLocation } from 'react-router-dom';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
export default function Inventory() {


  const location = useLocation();
  const inheritedData=JSON.parse(location.state.inv);
    const [inventory, setInventory] = React.useState(inheritedData);
    console.log(inheritedData)
    const Auth =React.useContext(AuthContext);
    const user = Auth.getUser()
    
     
    
    
    
    
    // empty dependency array means this effect will only run once (like componentDidMount in classes)
   
    return (
      <section>
        {inventory&&(       
           <><h1>Pokemons:</h1>
       
            {inventory.pokemonInventory.map(pokemon => (
              <Card sx={{ maxWidth: 345, marginLeft:'20%' }}>
              <CardMedia
                sx={{ width : '100px', height: '100px' }}
                image = {pokemon.imageURL}
                title="pokemon image"
              />
              <CardContent>
                <Typography gutterBottom variant="h5" component="div">
                  {pokemon.name}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  
<ul><li>
  <b>HP:</b>{pokemon.healthPoints}
  </li>
<li>
  <b>PowerAttack:</b>{pokemon.powerAttack}</li>
<li>
  <b>PowerDefense:</b>{pokemon.powerDefense}</li>
</ul>
                  
                  
                </Typography>
              </CardContent>
              </Card>
               
                
            ))}
       
        <h1>Items:</h1>
         
            {inventory.itemInventory.map(item => (
              <Card sx={{ maxWidth: 345, marginLeft:'20%' }}>

              <CardContent>
                <Typography gutterBottom variant="h5" component="div">
                {item.RES_IDENTIFIER}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  

  <b>Effect:</b>{item.action}

  
                  
                  
                </Typography>
              </CardContent>
              </Card>
            ))}
        </>)}
        <Button href='/play' variant='contained' color='secondary'>Go back</Button>
      </section>
    );
}
  
function readInventory(){
    const xhr = new XMLHttpRequest();
    xhr.open("GET", "http://localhost:8080/api/v1/game/inventory");
    xhr.onload = function(){

      if (xhr.readyState === 4 && xhr.status === 200) {
        console.log(xhr);
        return JSON.parse(xhr.responseText);
      } else {
        console.log(`Error: ${xhr.status}`);
      }
    };
    xhr.send();
    
  }
export default function Inventory() {
    const inventory = readInventory();
    return (
      <section>
        <h1>Pokemons:</h1>
         <ul>
            {inventory.pokemonInventory.map(pokemon => (
                <li>{pokemon.name}
                <img
                    src = {pokemon.imageURL}
                    alt="Katherine Johnson"
                    style={{ width : 50, height: 50 }}/></li>
            ))}
        </ul>
        <h1>Items:</h1>
         <ul>
            {inventory.itemsInventory.map(item => (
                <li>{item.RES_IDENTIFIER}
                {item.action}</li>
            ))}
        </ul>
      </section>
    );
}
  
function readInventory(){
    const xhr = new XMLHttpRequest();
    xhr.open("GET", "localhost:8080/api/v1/game/inventory");
    xhr.onload = function(){
      if (xhr.readyState === 4 && xhr.status === 201) {
        console.log(JSON.parse(xhr.responseText));
        return JSON.parse(xhr.responseText);
      } else {
        console.log(`Error: ${xhr.status}`);
      }
    };
    xhr.send(data);
    
  }
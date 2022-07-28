import './App.css';
import axios from "axios";

function App() {


  const handleClick = async () => {
      console.log("inside button click");
     const {data} = await axios.get("http://localhost:8080/test");
     console.log(data);
  }

  return (
    <div className="App">
      this is the main app
      <button onClick={handleClick}>Button</button>
    </div>
  );
}

export default App;

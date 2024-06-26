import { useEffect, useState } from "react";
import axios from "axios";

const ListarMotorHome = () => {
  const [motorhome, setmotorhome] = useState([]);
  useEffect(() => {
    axios
      .get(` http://localhost:8080/motorhome `)
      .then((res) => {
        setmotorhome(res.data);
      })
      .catch((err) => console.log(err));
  }, []);

  return (
    <div style={{ marginTop: 100, height: "100vh" }}>
      {motorhome.map((producto) => (
        <div key={producto.nombre}>
          <h1>MotorHome</h1>
          <h2>{producto.nombre}</h2>
          <h2>{producto.marca}</h2>
          <h2>{producto.modelo}</h2>
          <h2>{producto.anioFabricacion}</h2>
          <h2>{producto.descripcion}</h2>
          <h2>{producto.precioAlquiler}</h2>
          <img src={"/images/" + producto.file} alt="" width={200} />
        </div>
      ))}
    </div>
  );
};

export default ListarMotorHome;

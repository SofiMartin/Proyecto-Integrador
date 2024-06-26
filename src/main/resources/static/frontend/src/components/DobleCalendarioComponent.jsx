import { useState, useEffect } from "react";
import DatePicker from "react-datepicker";
import axios from "axios";
import "react-datepicker/dist/react-datepicker.css"; // Import default styles for the date picker

// FALTA LA LOGICA DESDE EL BACKEND

function BookingCalendar() {
  // useState fechas de checkin y checkout
  const [startDate, setStartDate] = useState(null);
  const [endDate, setEndDate] = useState(null);
  // useState para manejar fechas NO disponibles
  const [unavailableDates, setUnavailableDates] = useState([]);

  // Fetch de fechas no disponibles de la base de datos
  useEffect(() => {
    axios
      .get("backend-api-url/unavailable-dates")
      .then((response) => {
        // Asumiendo que la respuesta tiene un array de fechas
        setUnavailableDates(response.data);
      })
      .catch((error) => console.error(error));
  }, []);

  // Funcion para chequear si la fecha NO esta disponible
  const isUnavailable = (date) => {
    // Logica que compara si 'date' esta dentro de UnavailableDates'
    // Convierte la fecha a un formato que pueda ser comparable
    return unavailableDates.includes(/* fecha a comparar */);
  };

  // JSX para renderizar el calendario
  return (
    <div>
      <DatePicker
        selected={startDate}
        onChange={(dates) => {
          const [start, end] = dates;
          setStartDate(start);
          setEndDate(end);
        }}
        startDate={startDate}
        endDate={endDate}
        selectsRange
        inline
        excludeDates={unavailableDates.map((date) => new Date(date))}
        // Codigo para RESALTAR fechas no disponibles
        dayClassName={(date) =>
          isUnavailable(date) ? "unavailable-date" : undefined
        }
        // A esto se le puede dar estilo con CSS, como cambiar el color
      />
    </div>
  );
}

export default BookingCalendar;

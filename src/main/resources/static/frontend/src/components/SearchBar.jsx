import { useState, useEffect, useRef } from "react";
import Select from "react-select";
import { Button, Row, Col } from "reactstrap";
import { DateRangePicker } from "rsuite";
import "rsuite/dist/rsuite.min.css";
import axios from "axios";
import "./SearchBar.css";

const SearchBar = () => {
  const [categories, setCategories] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState(null);
  const [selectedMotorhome, setSelectedMotorhome] = useState(null);
  const [dateRange, setDateRange] = useState([new Date(), new Date()]);
  const [motorhomes, setMotorhomes] = useState([]);
  const [showCalendar, setShowCalendar] = useState(false);
  const [searchResults, setSearchResults] = useState([]);
  const calendarRef = useRef(null);
  const inputRef = useRef(null);

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const response = await axios.get("http://localhost:8080/categoria");
        const categoryOptions = response.data.map((category) => ({
          value: category.id,
          label: category.nombre,
        }));
        setCategories(categoryOptions);
      } catch (error) {
        console.error("Error fetching categories:", error);
      }
    };

    fetchCategories();
  }, []);

  const loadMotorhomes = async (categoryId) => {
    try {
      const response = await axios.get(
        `http://localhost:8080/motorhome/categoria/${categoryId}`
      );
      const motorhomeOptions = response.data.map((motorhome) => ({
        value: motorhome.id,
        label: motorhome.nombre,
      }));
      setMotorhomes(motorhomeOptions);
    } catch (error) {
      console.error("Error fetching motorhomes:", error);
      setMotorhomes([]);
    }
  };

  const handleCategoryChange = (selectedOption) => {
    setSelectedCategory(selectedOption);
    setSelectedMotorhome(null);

    if (selectedOption) {
      loadMotorhomes(selectedOption.value);
    }
  };

  // const handleDateClick = () => {
  //   setShowCalendar(!showCalendar);
  // };

  const handleSelect = (value) => {
    setDateRange(value);
  };

  const handleSearch = async () => {
    if (!selectedCategory) {
      console.error("Seleccione una categoría antes de buscar.");
      return;
    }

    try {
      // Obtener los IDs de productos reservados en el rango de fechas
      const responseReserva = await axios.get(
        "http://localhost:8080/reserva/buscarPorCategoriaYFechas",
        {
          params: {
            categoriaId: selectedCategory.value,
            fechaInicio: dateRange[0].toISOString().split("T")[0],
            fechaFin: dateRange[1].toISOString().split("T")[0],
          },
        }
      );

      if (responseReserva.data.length === 0) {
        console.log("No se encontraron reservas.");
        setSearchResults([]);

        const responseProductos = await axios.get(
          "http://localhost:8080/motorhome/categoria/" + selectedCategory.value
        );
        if (responseProductos.data.length === 0) {
          console.log("No se encontraron resultados después de filtrar.");
          setSearchResults([]);
          return;
        }

        console.log(responseProductos.data);
        setSearchResults(responseProductos.data);

        // return;
      } else {
        const reservedProductIds = responseReserva.data.map(
          (reserva) => reserva
        );
        console.log(reservedProductIds);

        // Filtrar productos por categoría y excluir los productos reservados
        const excludedProductIdsString = reservedProductIds.join(",");
        console.log(excludedProductIdsString);
        const responseProductos = await axios.get(
          "http://localhost:8080/motorhome/categoria/filtrar",
          {
            params: {
              categoriaIds: selectedCategory.value,
              excludedProductIds: excludedProductIdsString,
            },
          }
        );

        console.log(excludedProductIdsString);

        if (responseProductos.data.length === 0) {
          console.log("No se encontraron resultados después de filtrar.");
          setSearchResults([]);
          return;
        }

        console.log(responseProductos.data);
        setSearchResults(responseProductos.data);
      }
    } catch (error) {
      console.error("Error fetching search results:", error);
    }
  };

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (
        calendarRef.current &&
        !calendarRef.current.contains(event.target) &&
        inputRef.current &&
        !inputRef.current.contains(event.target)
      ) {
        setShowCalendar(false);
      }
    };

    document.addEventListener("mousedown", handleClickOutside);

    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, [calendarRef, inputRef]);

  useEffect(() => {
    const handleClickOutside = (event) => {
      // Si el clic no está dentro del contenedor del calendario ni dentro del botón que lo muestra, cierra el calendario
      if (
        calendarRef.current &&
        !calendarRef.current.contains(event.target) &&
        !document.getElementById("calendarButton")?.contains(event.target)
      ) {
        setShowCalendar(false);
      }
    };

    // Agrega el event listener al documento
    document.addEventListener("mousedown", handleClickOutside);

    // Remueve el event listener cuando el componente se desmonta
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, []);

  const handleDateClick = (date) => {
    setShowCalendar(true); // Al hacer clic en la fecha, se muestra el calendario
  };

  //   const handleSelect = (ranges) => {
  //   setDateRange([ranges.selection]);
  //   // setShowCalendar(false); // Quizás quieras ocultar el calendario aquí
  // };

  // const handleSelect = (ranges) => {
  //   setDateRange([ranges.selection]);
  //   const formattedStartDate = ranges.selection.startDate.toLocaleDateString();
  //   const formattedEndDate = ranges.selection.endDate.toLocaleDateString();
  //   document.getElementById('dateRangeText').innerText = `${formattedStartDate} - ${formattedEndDate}`;
  //   setShowCalendar(false);
  //   setShowCalendar(true); // Añade esto para mantener visible el calendario después de seleccionar las fechas
  // };
  const handleCalendarClose = () => {
    setShowCalendar(false);
  };

  return (
    <div className="form-with-background">
      <h2>Buscador de Motorhomes</h2>
      <h5 style={{ textAlign: "center", marginBottom: "20px" }}>
        Buscá aquí los Motorhomes que mejor se ajusten a tus necesidades:{" "}
      </h5>
      <Row className="row-cols-lg-auto g-4 align-items-center justify-content-center search-bar">
        <Col>
          <Select
            options={categories}
            value={selectedCategory}
            onChange={handleCategoryChange}
            placeholder="Seleccione una categoría..."
          />
        </Col>
        <Col>
          <div style={{ position: "relative", width: "300px" }} ref={inputRef}>
            <DateRangePicker
              value={dateRange}
              onChange={handleSelect}
              format="yyyy-MM-dd"
              placement="bottom"
            />
          </div>
        </Col>
        <Col>
          <Button
            color="primary"
            className="search-button --bs-orange"
            onClick={handleSearch}
          >
            Buscar
          </Button>
        </Col>
      </Row>

      {searchResults.length > 0 && (
        <Row className="mt-4">
          {searchResults.map((result) => (
            <Col key={result.id}>
              <div className="motorhome-card">
                <h3>{result.nombre}</h3>
                {/* Agrega más información del motorhome según tu modelo de datos */}
              </div>
            </Col>
          ))}
        </Row>
      )}
    </div>
  );
};

export default SearchBar;

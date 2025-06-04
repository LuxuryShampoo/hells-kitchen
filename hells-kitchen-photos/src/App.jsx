import './App.css';
import Contestants from './components/Contestants';
import SearchBar from './components/SearchBar';
import { useEffect, useState } from 'react';

function App() {
  const [query, setQuery] = useState('');
  const [contestants, setContestants] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchContestants = async () => {
      try {
        setLoading(true);
        const response = await fetch('http://localhost:3001/contestants');
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();
        setContestants(data);
      } catch (e) {
        setError(e.message);
      } finally {
        setLoading(false);
      }
    };
    fetchContestants();
  }, []);

  const handleSearch = (newQuery) => {
    setQuery(newQuery);
  };

  const filteredContestants = contestants.filter((contestant) =>
    contestant.name.toLowerCase().includes(query.toLowerCase())
  );

  if (loading) {
    return <div className="loading">Loading contestants...</div>;
  }

  if (error) {
    return <div className="error">Error fetching contestants: {error}</div>;
  }

  return (
    <div className="App">
      <header className="App-header">
        <h1>Hell's Kitchen Contestants</h1>
        <SearchBar onSearch={handleSearch} />
      </header>
      <Contestants contestants={filteredContestants} />
    </div>
  );
}

export default App; 
import React, { useState } from 'react';
import PropTypes from 'prop-types';
import './SearchBar.css';

const SearchBar = ({ onSearch }) => {
  const [inputValue, setInputValue] = useState('');

  const handleChange = (event) => {
    setInputValue(event.target.value);
    onSearch(event.target.value); //实时搜索
  };

  // Optional: if you want a search button
  /*
  const handleSubmit = (event) => {
    event.preventDefault();
    onSearch(inputValue);
  };
  */

  return (
    <form className="search-bar-form" onSubmit={(e) => e.preventDefault()}> {/* Prevent default form submission if button is not used */}
      <input
        type="text"
        placeholder="Search contestants..."
        value={inputValue}
        onChange={handleChange}
        className="search-bar-input"
      />
      {/* Optional: Search button */}
      {/* <button type="submit" className="search-bar-button">Search</button> */}
    </form>
  );
};

SearchBar.propTypes = {
  onSearch: PropTypes.func.isRequired,
};

export default SearchBar; 
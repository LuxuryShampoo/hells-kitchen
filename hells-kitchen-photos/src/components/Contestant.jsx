import React from 'react';
import PropTypes from 'prop-types';
import './Contestant.css';

const Contestant = ({ contestant }) => {
  return (
    <div className="contestant-card">
      <img src={contestant.img} alt={contestant.name} className="contestant-image" />
      <div className="contestant-info">
        <h2 className="contestant-name">{contestant.name}</h2>
        {/* Add more details if needed */}
      </div>
    </div>
  );
};

Contestant.propTypes = {
  contestant: PropTypes.shape({
    img: PropTypes.string.isRequired,
    name: PropTypes.string.isRequired,
    // Add more prop types if needed
  }).isRequired,
};

export default Contestant; 
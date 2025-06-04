import React from 'react';
import PropTypes from 'prop-types';
import Contestant from './Contestant';
import './Contestants.css';

const Contestants = ({ contestants }) => {
  if (!contestants || contestants.length === 0) {
    return <p className="no-contestants">No contestants found.</p>;
  }

  return (
    <div className="contestants-grid">
      {contestants.map((contestant) => (
        <Contestant key={contestant.id || contestant.name} contestant={contestant} />
      ))}
    </div>
  );
};

Contestants.propTypes = {
  contestants: PropTypes.arrayOf(
    PropTypes.shape({
      id: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
      name: PropTypes.string.isRequired,
      // Ensure other necessary props for Contestant are included here if not optional
    })
  ),
};

Contestants.defaultProps = {
  contestants: [],
};

export default Contestants; 
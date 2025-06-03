import React, { useState } from 'react';
// ...existing code...

function App() {
  // ...existing code...
  const [miiClicked, setMiiClicked] = useState(false);

  // ...existing code...

  return (
    <div>
      {/* ...existing code... */}
      <button
        onClick={() => setMiiClicked(true)}
        style={{
          border: miiClicked ? '1px solid transparent' : '2px solid #ffe066',
          boxShadow: miiClicked ? 'none' : '0 0 6px 0 #ffe06655',
          transition: 'border 0.2s, box-shadow 0.2s'
        }}
        disabled={miiClicked}
      >
        mii
      </button>
      {/* ...existing code... */}
      <button
        style={{
          background: miiClicked ? '' : '#ccc',
          color: miiClicked ? '' : '#888',
          cursor: miiClicked ? 'pointer' : 'not-allowed',
          pointerEvents: miiClicked ? 'auto' : 'none',
          transition: 'background 0.2s, color 0.2s'
        }}
        disabled={!miiClicked}
      >
        level one
      </button>
      {/* ...existing code... */}
    </div>
  );
}

// ...existing code...
export default App;

// dataProcessor.js

self.onmessage = function(event) {
    const { data, start, end } = event.data;
  
    // Process a chunk of data
    const processedData = data.slice(start, end).map(item => ({
      lat: item.lat,
      lng: item.lon, 
      weight: Math.abs(item.value) 
    }));
  
    // Post processed chunk back to the main thread
    self.postMessage(processedData);
  };
  
//A convertor program to convert NetCDF files to JSON objects

import ucar.ma2.Array;
import ucar.ma2.Index;
import ucar.nc2.NetcdfFile;
import ucar.nc2.NetcdfFiles;
import ucar.nc2.Variable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String filename = "december";
        String filePath = "data/" + filename + ".nc"; // Specify the path to your NetCDF file

        try {
            NetcdfFile netcdfFile = NetcdfFiles.open(filePath);
            System.out.println("NetCDF file opened successfully.");

            // Retrieve the variables (e.g., "lat", "lon", "chl")
            Variable latVar = netcdfFile.findVariable("LAT1");
            Variable lonVar = netcdfFile.findVariable("LON1");
            Variable chlVar = netcdfFile.findVariable("CHL");

            if (latVar == null || lonVar == null || chlVar == null) {
                System.out.println("One or more variables not found in the NetCDF file.");
                netcdfFile.close();
                return;
            }

            // Read data from the variables
            Array latitudes = latVar.read();
            Array longitudes = lonVar.read();
            Array chlValues = chlVar.read();

            int latSize = (int) latitudes.getSize();
            int lonSize = (int) longitudes.getSize();

            try (FileWriter file = new FileWriter("data/" + filename + "/chlorophyll_data.json")) {
                // Begin writing JSON array
                file.write("{\"chlorophyll_data\": [\n");

                boolean first = true;
                // Loop through all data points for both lat and lon
                for (int latIdx = 0; latIdx < latSize; latIdx++) {
                    for (int lonIdx = 0; lonIdx < lonSize; lonIdx++) {
                        double chlValue = chlValues.getDouble(chlValues.getIndex().set(latIdx, lonIdx));

                        // Filter out chl values less than 0
                        if (chlValue < 0) {
                            continue; // Skip this iteration if the chl value is less than 0
                        }

                        // Write comma between objects, except before the first one
                        if (!first) {
                            file.write(",\n");
                        } else {
                            first = false;
                        }

                        // Write the current JSON object
                        file.write(String.format("{\"lat\": %.6f, \"lon\": %.6f, \"value\": %.6f}",
                                latitudes.getDouble(latitudes.getIndex().set(latIdx)),
                                longitudes.getDouble(longitudes.getIndex().set(lonIdx)),
                                chlValue));
                    }
                }

                // End JSON array
                file.write("\n]}");
                System.out.println("JSON file created: chlorophyll_data.json");
            }

            netcdfFile.close();

            File netcdf = new File(filePath);
            if (netcdf.exists() && netcdf.delete()) {
                System.out.println(".nc file deleted successfully.");
            } else {
                System.out.println("Failed to delete .nc file.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

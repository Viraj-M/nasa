A Three JS project to make a globe viewer that can view different data types from NASA satellites. It has pre-plotted images of the data and applies them as textures to a globe along with land overlay.

Its features include:
 1) viewing data from PACE and Aqua-MODIS satellite, namely chlorophyll, Particulate Organic Carbon, Phytoplankton Carbon, Remote Sensing Reflectance, Apparent Visible Wavelength and Sea Surface   Temperature
 2) The data is displayed on a monthly time axis. For Aqua-MODIS, monthly climatology since launch is taken, and for PACE, monthly data for 2024 is taken
 3) There is an option to make the data layer transparent, allowing the user to see parts of the globe where data is not present. E.g. poles. When enabled, this takes the data layer to the frontmost, which makes data for rivers, lakes and other inland water bodies visible since otherwise the land textures would cover them.
 4) A colour bar is pre-programmed with the scales of all the datasets where applicable(some datasets inherently do not have a scale).
 5) Uploading a PNG file for additional pre-plotted data is available. This is rendered as a texture in the data layer.
 6) The "auto-rotate" button disables panning, zooming, etc, of the globe by the user interface. It locks the globe in default position before gently rotating it around its axis.

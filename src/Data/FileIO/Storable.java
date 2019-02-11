package Data.FileIO;

import java.io.*;

/**
 * A  class to add fileIO to any class
 */
public abstract class Storable implements Serializable {
    private String filePath;

    public Storable(String filePath){
        this.filePath = filePath;
    }

    /**
     * Loads an Object from a file
     * @param filePath the path to the stored file
     * @return The object from the loaded file
     * @throws Exception Possible errors
     */
    public static Storable loadFromFile(String filePath) throws Exception{
        ObjectInputStream inputStream =
            new ObjectInputStream(
                new FileInputStream(
                    filePath
                )
            );
        Storable loadedObject = (Storable) inputStream.readObject();
        inputStream.close();
        loadedObject.setFilePath(filePath);
        return loadedObject;
    }

    /**
     * Returns the filepath where this object is saved using this.save();
     * @return the filepath where this object is saved using this.save();
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * sets the filepath where this object is saved using this.save();
     * @param filePath the filepath where this object is saved using this.save();
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Saves this object to a file on the given filepath
     * @param filePath
     * @throws Exception
     */
    public void saveAs(String filePath) throws Exception{
        ObjectOutputStream outputStream =
            new ObjectOutputStream(
                new FileOutputStream(
                    filePath
                )
            );
        outputStream.writeObject(this);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * Saves this object to the preset file
     * @throws Exception Possible errors
     */
    public void save() throws Exception{
        this.saveAs(this.filePath);
    }
}

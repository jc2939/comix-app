import pandas as pd
import json
import re

# input and output files
csv_file_path = "./raw_db_comics.csv"
json_output_file_path = "./output.json"

# Read the csv file
# the first two data rows in the csv are empty so they are skippedd
df = pd.read_csv(csv_file_path, skiprows=range(1, 3))

json_output = []  # Initialize the json output list

# Start comic IDs at 1
nextID = 1


def parse_volume(series):
    """Extracts any volume info from the series title using regex."""
    # Search for volume information in the series name
    match = re.search(r"Vol\.? (\d+)", series, re.IGNORECASE)
    if match:
        return match.group(1)
    return "1"  # default the volume to "1" if it wasn't found in the series tilte


# Iterate through each row
for idx, row in df.iterrows():
    # Initialize this ConcreteComic object
    comic_obj = {}

    # Publisher
    comic_obj["publisher"] = (
        row["Publisher"] if pd.notna(row["Publisher"]) else "<Unknown Publisher>"
    )

    # Series and volume
    series = row["Series"] if pd.notna(row["Series"]) else "<Unknown Series>"
    comic_obj["series"] = series
    comic_obj["volume"] = parse_volume(series)

    # Issue
    comic_obj["issue"] = row["Issue"] if pd.notna(row["Issue"]) else "<Unknown Issue>"

    # Publication date
    comic_obj["publicationDate"] = (
        row["Release Date"]
        if pd.notna(row["Release Date"])
        else "<Unknown Publication Date>"
    )

    # Comic title
    comic_obj["comicTitle"] = (
        row["Full Title"] if pd.notna(row["Full Title"]) else "<Unknown Title>"
    )

    # Creators list
    if pd.notna(row["Creators"]):
        comic_obj["creators"] = row["Creators"].split(" | ")
    else:
        comic_obj["creators"] = []

    # Set principle characters to None (no csv data)
    comic_obj["principleCharacters"] = []

    # Decription
    comic_obj["description"] = (
        row["Variant Description"] if pd.notna(row["Variant Description"]) else ""
    )

    # Set value as None (no csv data)
    comic_obj["value"] = None

    json_output.append(comic_obj)

json_output = {"userName": "database", "comics": json_output}

# generate the json string
json_output_str = json.dumps(json_output, indent=4)

# Save the json string to the output file with pretty formatting
with open(json_output_file_path, "w") as f:
    json.dump(json_output, f, indent=4)

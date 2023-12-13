import ComicCard from "./ComicCard";
import {useUser} from "../UserContext";
import {Container} from "reactstrap";

const ComicBoard = (props) => {

	// eslint-disable-next-line no-unused-vars
	const [currentUser, setCurrentUser] = useUser()
	const comicData = props.comics.map((comic, index) => {
		return <ComicCard
			key={index}
			user={currentUser}
			comicKey={comic.comicID}
			publisher={comic.publisher}
			series={comic.series}
			volume={comic.volume}
			issue={comic.issue}
			publicationDate={comic.publicationDate}
			comicTitle={comic.comicTitle}
			creators={comic.creators}
			principleCharacters={comic.principleCharacters}
			description={comic.description}
			value={comic.value}
			comic={comic.comic}
		/>
	})

	return (
		<Container>
			<h1 className={"Poppins-Bold text-white"}> Surf The Database</h1>
			<hr style={{
				height: '2px',
				borderWidth: '0',
				color: 'white',
				backgroundColor: 'white',
				marginBottom: '40px'
			}} />
			<div className={"w-100 comicGrid"}>
				{comicData}
			</div>
		</Container>

	)
}
export default ComicBoard
const CollectionFilter = () => {
	return (
		<div>
			<h3> Filter By: </h3>
			<div>
				<h6> Publisher </h6>
				<form className={'filter-options'}>
					<label>
						<input className={'me-3'} type={'radio'}/>
						DC Comics
					</label>
					<label>
						<input className={'me-3'} type={'radio'}/>
						Marvel Comics
					</label>
					<label>
						<input className={'me-3'} type={'radio'}/>
						Action Comics
					</label>
					<label>
						<input className={'me-3'} type={'radio'}/>
						Dark Horse Comics
					</label>
				</form>
			</div>
		</div>
	)
}

export default CollectionFilter;
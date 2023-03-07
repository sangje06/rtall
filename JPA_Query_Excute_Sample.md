import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor 
public class CustomQueryRepository extends QuerydslRepositorySupport {

	public List<ObjectNode> executeQuety(String query) {
		Query native = getEntitymanager()
			.createNativeQuery(query, Tuple.class);
			// setTimeout 사용 시 추가
			//	.unwrap(org.hivernate.query.Query.class)
			//	.setTimeout(20);
		List<Tuple> results = native.getResultList();
		List<ObjectNode> json = _toJson(results);
		return json;
	}
	
	private List<ObjectNode> _toJson(List<Tuple> results) {
		List<ObjectNode> json = new ArrayList<>();
		ObjectMapper om = new ObjectMapper();
		for(Tuple t : results) {
			List<TupleElement<?>> cols = t.getElements();
			ObjectNode node = om.createObjectNode();
			for (TupleElements col : cols) {
				node.put(col.getAlias(), t.get(col.getAlias())==null?"":t.get(co.getAlias()).toString()); // null 인경우 체크
			}
			json.add(node);
		}
		return json;
	}
}
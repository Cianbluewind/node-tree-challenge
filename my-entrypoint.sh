echo "Running Backup & Restore"	
neo4j-admin load --from=/neo4j.dump --database=neo4j --force
chown -R neo4j:neo4j /data
/docker-entrypoint.sh neo4j start